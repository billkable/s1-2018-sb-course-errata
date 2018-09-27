package io.pivotal.demo.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActuatorEndpointDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActuatorEndpointDemoApplication.class, args);
    }

    @Bean
    BackingServiceEndpoint endpoint() {
        return new BackingServiceEndpoint();
    }
}
