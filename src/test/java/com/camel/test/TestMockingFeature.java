package com.camel.test;


import java.net.ConnectException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TestMockingFeature extends CamelTestSupport {


    @Override
    protected CamelContext createCamelContext () throws Exception {
        CamelContext context = super.createCamelContext();
        context.addComponent("jms", context.getComponent("seda"));
        return context;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("jms:topic:quote").to("mock:quote")
                        .process(new Processor() {

                            public void process(Exchange exchange) throws Exception {
                                throw new ConnectException("Simulated connection error");
                            }
                        });
            }
        };
    }

    @Test
    public void testQuote () throws Exception{
        MockEndpoint quote = getMockEndpoint("mock:quote");
        quote.expectedMessageCount(2);
        quote.expectedBodiesReceived("Hello Camel !!!","Camel rocks !!!");
        template.sendBody("jms:topic:quote", "Hello Camel !!!");
        template.sendBody("jms:topic:quote", "Camel rocks !!!");
        quote.assertIsSatisfied();
    }

}
