package com.camel.test.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelMQRouter extends RouteBuilder {

    public void configure() throws Exception {
        from("timer:hello?period=1000").
                transform(simple("Random number ${random(0,100)}"))
                .to("rabbitmq:foo");

        from("rabbitmq:foo").
                log("From RabbitMQ: ${body}");
    }
}
