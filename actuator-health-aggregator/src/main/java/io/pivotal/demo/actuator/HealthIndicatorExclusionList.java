package io.pivotal.demo.actuator;

public interface HealthIndicatorExclusionList {
    boolean inExclusionList(String indicatorName);
}
