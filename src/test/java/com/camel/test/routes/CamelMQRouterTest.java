package com.camel.test.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelMQRouterTest extends CamelTestSupport {

    protected RouteBuilder routeBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("rabbitmq:foo").to("mock:quote");
            }
        };
    }

    @Test
    public void testMQRoute() throws InterruptedException {
        MockEndpoint mockedEndpoint = getMockEndpoint("mock:quote");
        mockedEndpoint.setExpectedCount(1);
        template.sendBody("rabbitmq:foo");
        mockedEndpoint.assertIsSatisfied();

    }

}