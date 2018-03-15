package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyAppRouter extends RouteBuilder {

    public void configure() throws Exception {
        from("direct:hello")
            .transform(simple("{ \"message\": \"Hello from ${sysenv.HOSTNAME}\"}"));
    }
}
