package org.example.formazione.soap.contractfirst;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;


import java.util.HashMap;

public class CamelRoutes extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        //This is our first endpoint in the Readme, using the Xpath camel component with local names
        from("cxf:bean:personEndpointXpathComponent")
                .routeId("cxf:bean:personEndpointXpathComponent")
                .setHeader("ProvaNome").xpath("//*[local-name()='inputPerson']/firstName/text()", String.class)
                .log("headers ${headers}")
                .setBody(constant("OK"))
                .to("seda:incomingOrders");

        //This is our second endpoint in the Readme, using java code and XpathBuilder with namespaces
        from("cxf:bean:personEndpointXpathJava")
                .routeId("cxf:bean:personEndpointXpathJava")
                .convertBodyTo(java.lang.String.class)
                .process((exchange -> {
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
                .convertBodyTo(java.lang.String.class)
                .to("seda:incomingOrders");

        //This is our third endpoint in the Readme, using a processor and a Pojo instead of a message
        from("cxf:bean:personEndpointPojo")
                .routeId("cxf:bean:personEndpointPojo")
                .process(new ProvaProcessor())
                .log("headers ${headers}")
                .setBody(constant("OK"))
                .convertBodyTo(java.lang.String.class)
                .to("seda:incomingOrders");


        from("seda:incomingOrders")
                .routeId("seda:incomingOrders")
                .to("mock:end");


    }
}
