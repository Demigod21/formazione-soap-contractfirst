package org.example.formazione.soap.contractfirst;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.model.dataformat.JaxbDataFormat;

import java.util.HashMap;
import java.util.Map;

public class CamelRoutes extends RouteBuilder {


    @Override
    public void configure() throws Exception {



        from("cxf:bean:personEndpoint")
                .routeId("cxf:bean:personEndpoint")
                .convertBodyTo(java.lang.String.class)
                .log("Boody DOPO CONVERT TO STRING ${body}")
                .process((exchange -> {
                    String s1 = exchange.getIn().getBody().toString();
                    exchange.getIn().setHeader("ssss", s1);

                    XPathBuilder builder = new XPathBuilder("/soapenv:Envelope/soapenv:Body/con:inputPerson/firstName/text()");
                    HashMap m1 = new HashMap();
                    m1.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                    m1.put("con", "http://contractfirst.soap.formazione.example.org");
                    builder.setNamespaces(m1);
                    String val = builder.evaluate(exchange, String.class);
                    exchange.getIn().setHeader("ProvaNome", val);

                }))
                .log("headers ${headers}")
                .setBody(constant("OK"))
                .log("Boody DOPO SET CONSTANT ${body}")
                .convertBodyTo(java.lang.String.class)
                .to("seda:incomingOrders");

        from("seda:incomingOrders")
                .routeId("seda:incomingOrders")
                .to("mock:end");


    }
}
