package io.pivotal.demo.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class TestConfig {
    static final String CUSTOM_HEALTH_INDICATOR_NAME = "custom1";

    @Bean
    public HealthIndicator custom1HealthIndicator(){
        return () -> Health.down().build();
    }

    @Bean
    public HealthIndicatorExclusionList healthIndicatorExclusionList() {
        return new HealthIndicatorExclusionList() {
            private final Set<String> exclusionList = new HashSet<>(
                    // TODO consider parameterizing from external properties in implementation
                    Collections.singletonList(CUSTOM_HEALTH_INDICATOR_NAME)
            );

            public boolean inExclusionList(String indicatorName) {
                return this.exclusionList.contains(indicatorName);
            }
        };
    }

    @Bean
    public HealthAggregator exclusionOrderedHealthAggregator() {
        return new ExclusionOrderedHealthAggregator(healthIndicatorExclusionList());
    }
}