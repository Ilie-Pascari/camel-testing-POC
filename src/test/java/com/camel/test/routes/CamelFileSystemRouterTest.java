package com.camel.test.routes;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelFileSystemRouterTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new CamelFileSystemRouter();
    }

    public void setUp() throws Exception {
        deleteDirectory("from");
        deleteDirectory("out");
        super.setUp();
    }

    @Test
    public void testFileMoving() throws InterruptedException {
        template.sendBodyAndHeader("file://from", "Hello World", Exchange.FILE_NAME, "hello_world.txt");
        Thread.sleep(1000);
        File target = new File("out/hello_world.txt");
        assertTrue("File not moved", target.exists());
        String content = context.getTypeConverter().convertTo(String.class, target);
        assertEquals("Hello World", content);
    }

}