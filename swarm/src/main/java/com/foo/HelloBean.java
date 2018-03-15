package com.foo;

import javax.inject.Singleton;

import org.apache.camel.util.InetAddressUtil;

@Singleton
public class HelloBean {

    public String sayHello() throws Exception {
        String answer = "Swarm says hello from "
            + InetAddressUtil.getLocalHostName();
        return answer;
    }
}
