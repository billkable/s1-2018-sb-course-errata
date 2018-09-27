package io.pivotal.demo.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

import java.util.ArrayList;
import java.util.List;

@Endpoint(id = "new-backing-resource")
public class BackingServiceEndpoint {
    private final List<String> events =
            new ArrayList<>();

    @WriteOperation
    public void doCommand(String eventName) {
        events.add(eventName);
    }

    @ReadOperation
    public List<String> getEvents() {
        return events;
    }
}
