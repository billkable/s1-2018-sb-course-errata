package io.pivotal.demo.actuator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActuatorEndpointDemoApplicationTests {
    private final static String COMMAND_EVENT = "Command executed";
    private static final String ACTUATOR_NEW_BACKING_RESOURCE = "/actuator/new-backing-resource";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BackingServiceEndpoint endpoint;

    @Test
    public void testWriteEndpointViaHttp() {
        Map<String,String> postParams = new HashMap<>();

        postParams.put("eventName", COMMAND_EVENT);

        ResponseEntity responseEntity =
                restTemplate.exchange(
                        RequestEntity.post(URI.create(ACTUATOR_NEW_BACKING_RESOURCE))
                                .body(postParams),
                        Object.class);

        Assert.assertEquals(HttpStatus.NO_CONTENT,
                responseEntity.getStatusCode());

        Assert.assertEquals(1,endpoint.getEvents().size());

    }

    @Test
    public void testReadEndpointViaHttp() {
        Assert.assertEquals(COMMAND_EVENT,
                            (String)restTemplate.getForEntity(
                                        ACTUATOR_NEW_BACKING_RESOURCE,
                                        ArrayList.class)
                                    .getBody().get(0));
    }
}
