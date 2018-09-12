package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Uses Camel Retry.
 */
@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // use Camel retry
        // to retry up till 10 times with 1 second delay
//        onException(Exception.class)
//            .redeliveryDelay(1000)
//            .maximumRedeliveries(10);


        from("timer:foo?period=1000")
            .to("http4:helloswarm:8080/hello?connectionClose=true")
            .log("${body}");
    }
}



