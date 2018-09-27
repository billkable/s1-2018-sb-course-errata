package io.pivotal.demo.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An example of filtering an arbitrary Health Indicator from the
 * Aggregate Health Status
 *
 * This is based from collapsing the
 * `org.springframework.boot.actuate.health.OrderedHealthAggregator` and
 * `org.springframework.boot.actuate.health.AbstractHealthAggregator`
 * implementations.
 *
 * Compare the `aggregate()` method with that of
 * `AbstractHealthAggregator` to get idea of how the filtering is done
 */
public class ExclusionOrderedHealthAggregator
        implements HealthAggregator {

    private List<String> statusOrder;
    private HealthIndicatorExclusionList exclusionList;

    /**
     * Create a new {@link OrderedHealthAggregator} instance.
     */
    public ExclusionOrderedHealthAggregator(HealthIndicatorExclusionList exclusionList) {
        this.exclusionList = exclusionList;
        setStatusOrder(Status.DOWN, Status.OUT_OF_SERVICE, Status.UP, Status.UNKNOWN);
    }

    /**
     * Set the ordering of the status.
     * @param statusOrder an ordered list of the status
     */
    private void setStatusOrder(Status... statusOrder) {
        String[] order = new String[statusOrder.length];
        for (int i = 0; i < statusOrder.length; i++) {
            order[i] = statusOrder[i].getCode();
        }
        setStatusOrder(Arrays.asList(order));
    }

    /**
     * Set the ordering of the status.
     * @param statusOrder an ordered list of the status codes
     */
    private void setStatusOrder(List<String> statusOrder) {
        Assert.notNull(statusOrder, "StatusOrder must not be null");
        this.statusOrder = statusOrder;
    }

    private Status aggregateStatus(List<Status> candidates) {
        // Only sort those status instances that we know about
        List<Status> filteredCandidates = new ArrayList<>();
        for (Status candidate : candidates) {
            if (this.statusOrder.contains(candidate.getCode())) {
                filteredCandidates.add(candidate);
            }
        }
        // If no status is given return UNKNOWN
        if (filteredCandidates.isEmpty()) {
            return Status.UNKNOWN;
        }
        // Sort given Status instances by configured order
        filteredCandidates.sort(new StatusComparator(this.statusOrder));

//        return Status.UP;
        return filteredCandidates.get(0);
    }

    private Map<String, Object> aggregateDetails(Map<String, Health> healths) {
        return new LinkedHashMap<>(healths);
    }

    @Override
    public final Health aggregate(Map<String, Health> healths) {
        // Status aggregate filtering customization done to OOB here
        Map<String, Health> statusAggregateHealths =
                healths.entrySet().stream()
                        .filter(health -> !this.exclusionList.inExclusionList(health.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue));
        // Status aggregate filtering done

        List<Status> statusCandidates = statusAggregateHealths.values().stream()
                .map(Health::getStatus)
                .collect(Collectors.toList());
        Status status = aggregateStatus(statusCandidates);
        Map<String, Object> details = aggregateDetails(healths);
        return new Health.Builder(status, details).build();
    }
    /**
     * {@link Comparator} used to order {@link Status}.
     */
    private class StatusComparator implements Comparator<Status> {

        private final List<String> statusOrder;

        StatusComparator(List<String> statusOrder) {
            this.statusOrder = statusOrder;
        }

        @Override
        public int compare(Status s1, Status s2) {
            int i1 = this.statusOrder.indexOf(s1.getCode());
            int i2 = this.statusOrder.indexOf(s2.getCode());
            return (i1 < i2) ? -1 : (i1 != i2) ? 1 : s1.getCode().compareTo(s2.getCode());
        }

    }

}