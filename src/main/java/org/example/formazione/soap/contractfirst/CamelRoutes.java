package org.example.formazione.soap.contractfirst;

import org.apache.camel.builder.RouteBuilder;

public class CamelRoutes extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("cxf:bean:personEndpoint")
                .routeId("cxf:bean:personEndpoint")
                .convertBodyTo(java.lang.String.class)
                .log("Boody ${body}")
                .to("seda:incomingPerson");

        from("seda:incomingPerson")
                .routeId("seda:incomingPerson")
                .to("mock:end");


    }
}
