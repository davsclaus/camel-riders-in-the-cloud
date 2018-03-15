package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Uses the Hystrix Circuit Breaker.
 *
 * See also the <tt>application.properties</tt> file where
 * Hystrix has been configured, and the <tt>src/main/fabric8/service.yml</tt> file
 * which adds a label to instruct the Turbine/Hystrix Dashboard that this
 * container runs Hystrix.
 */
@Component
public class MyRoute extends RouteBuilder {

    // the configuration value (fallback)
    // has been defined in the <tt>src/main/fabric8/deployment.yml</tt> file

    // inject configuration via spring-style @Value
    @Value("${fallback}")
    private String fallback;

    @Override
    public void configure() throws Exception {


        onException(Exception.class)
            .maximumRedeliveries(10)
            .redeliveryDelay(1000);

        from("timer:foo?period=1000")
            .hystrix().id("call-helloswarm")
                // use Camel {{service}} to lookup service (pluggable service providers)
                .to("http4:{{service:helloswarm}}/hello?connectionClose=true")
                // just use DNS name to lookup service in k8s
//                .to("http4:helloswarm:8080/hello?connectionClose=true")
            .onFallback()
                .setBody().simple("${sysenv.FALLBACK}") // inject via Camel simple language
            .end()
            .log("${body}");
    }
}



