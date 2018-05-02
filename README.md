# Camel integration with Spring Boot and Red Hat Fuse on OpenShift

Two microservices using Spring Boot and WildFly Swarm with Apache Camel running on OpenShift.

There are these Maven projects:

* boot - Spring Boot application with Camel that triggers every 2nd second to call the hello service and log the response.
* swarm - WildFly Swarm application hosting a hello service which returns a reply message.

The diagram below illustrates this:

![Overview](diagram.png?raw=true "Overview")


### Slides and Video

This source code is used for the Red Hat Summit 2018 conference, and you can find the slides for the talks in the [slides](slides) directory.


### Prepare shell

When using Maven tooling you want to setup your command shell for OpenShift which can be done by

    minishift docker-env

Which tells you how to setup using eval

    eval $(minishift docker-env)


### Deploying WildFly Swarm (server)

You can deploy the WildFly Swarm application which hosts the hello service.

    cd swarm
    mvn install

If the build is success you can deploy to Kubernetes using:

    mvn fabric8:deploy


### Deploying Spring Boot (client)

You can deploy the Spring Boot application which is the client calling the hello service

    cd boot
    mvn install

If the build is success you can deploy to OpenShift using:

    mvn fabric8:deploy

You should then be able to show the logs of the client, by running `oc get pods` and find the name of the pod that runs the client, and then use `oc logs -f pod-name` to follow the logs.

However you can also run the application from the shell and have logs automatic tailed using

    mvn fabric8:run

And then when you press `cltr + c` then the application is undeployed. This allows to quickly run an application and stop it easily as if you are using `mvn spring-boot:run` or `mvn wildfly-swarm:run` etc.



### Installing Hystrix Dashboard on Kubernetes

The `boot` application which uses Hystrix can be viewed from the Hystrix Dashboard.

To install the dashboard you first need to install a hystrix stat collector which is called Turbine:

    oc create -f http://repo1.maven.org/maven2/io/fabric8/kubeflix/turbine-server/1.0.28/turbine-server-1.0.28-openshift.yml
    oc policy add-role-to-user admin system:serviceaccount:myproject:turbine
    oc expose service turbine-server

Then you can install the Hystrix Dashboard:

    oc create -f http://repo1.maven.org/maven2/io/fabric8/kubeflix/hystrix-dashboard/1.0.28/hystrix-dashboard-1.0.28-openshift.yml
    oc expose service hystrix-dashboard --port=8080

You should then be able to open the Hystrix Dashboard via

    minishift openshift service hystrix-dashboard

