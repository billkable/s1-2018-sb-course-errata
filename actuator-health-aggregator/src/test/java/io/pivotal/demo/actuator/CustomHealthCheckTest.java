package io.pivotal.demo.actuator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomHealthCheckTest {
    @Autowired
    private TestRestTemplate template;

    @Test
    public void testExcludedActuatorHealth() {

        ResponseEntity response =
                this.template.getForEntity("/actuator/health",
                        String.class);

        Assert.assertTrue(response.getBody().toString()
                .contains(TestConfig.CUSTOM_HEALTH_INDICATOR_NAME));

        Assert.assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
    }

}
