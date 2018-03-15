package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRestRouter extends RouteBuilder {

    @Override
    public void configure() {
        // you can also configure this in application.properties
        restConfiguration()
            .component("servlet")
            .contextPath("foo")
            .apiContextPath("api-doc");

        rest("/hello").produces("application/json")
            .get().description("Returns a hello response")
                .to("direct:hello");
    }

}
