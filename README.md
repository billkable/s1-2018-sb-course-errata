# Spring One 2018 Spring Boot Course Q&A

## Course

-   If you have not already done so, please do the
    [evaluation](https://docs.google.com/forms/d/1DOM5dLXaeAQYX9BLuwRyYfQWUazB-4bGiIr5c035kv4/viewform?edit_requested=true)
    of the workshop - Choose "Spring Boot" (The 3rd box out of 5)

## Instructors

-   Introduction of Instructors
    - Mark Secrist [msecrist@pivotal.io](mailto:msecrist@pivotal.io)
    - Bill Kable [bkable@pivotal.io](mailto:bkable@pivotal.io)

## Actuator Q & A

We did not get to the labs, and based from Q&A there are a few items
for follow up:

1.  One statement made in error during presentation:
    The `UNKNOWN` status maps to 200 HTTP response code, not 503.

1.  Question:
    Is it possible to exclude a health indicator from the
    Health status aggregation for the hosting application?

    Answer:
    Not out-of-box.
    However, it is possible to override the `HealthAggregator` to
    customize the Health indicator and status aggregations by overriding
    it with a custom `HealthAggregator` bean.

    See the following for more information:

    - [Spring boot actuator health information](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-health)
    - [Example code for filtering a custom health check status from app's app health status](./actuator-health-aggregator)

1.  How to configure custom actuator endpoints?
    A use case might be to add custom management logic to your app to
    expose through JMX or Actuator HTTP endpoint.

    See the following for more information:

    - [Implementing custom endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-endpoints-custom)
    - [Example code for adding a custom endpoint](./actuator-custom-endpoint)

Feel free to reach out with questions via instructor email.