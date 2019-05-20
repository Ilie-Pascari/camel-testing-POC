package com.camel.test.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelFileSystemRouter extends RouteBuilder {

    public void configure() throws Exception {
        from("file://from").to("file://out");
    }
}