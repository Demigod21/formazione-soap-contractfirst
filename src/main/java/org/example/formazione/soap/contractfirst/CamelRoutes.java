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
                .setHeader("ProvaNome").xpath("//*[local-name()='inputPerson']/firstName/text()", String.class) //Getting the value from xpath and setting it in our header
                .log("headers ${headers}") //If we look at this log, in particular at the header "ProvaNome", we'll see that it now contains the name specified in our soap request
                .setBody(constant("OK"))
                .to("seda:incomingOrders");

        //This is our second endpoint in the Readme, using java code and XpathBuilder with namespaces
        from("cxf:bean:personEndpointXpathJava")
                .routeId("cxf:bean:personEndpointXpathJava")
                .convertBodyTo(java.lang.String.class)
                .process((exchange -> {
                    XPathBuilder builder = new XPathBuilder("/soapenv:Envelope/soapenv:Body/con:inputPerson/firstName/text()");
                    HashMap nameSpacesMap = new HashMap(); //we're creating an hashmap because we need to define the namespaces that are used in the xpath builder, specifically "soapenv" and "con"
                    nameSpacesMap.put("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                    nameSpacesMap.put("con", "http://contractfirst.soap.formazione.example.org");
                    builder.setNamespaces(nameSpacesMap); //if we don't add this namespace map, xpath won't know what "soapenv" and "con" are
                    String val = builder.evaluate(exchange, String.class); //with "text()" we obtain the node, but we only need it's value, so we use EVALUATE
                    exchange.getIn().setHeader("ProvaNome", val); //Setting our header
                }))
                .log("headers ${headers}") //If we look at this log, in particular at the header "ProvaNome", we'll see that it now contains the name specified in our soap request
                .setBody(constant("OK"))
                .convertBodyTo(java.lang.String.class)
                .to("seda:incomingOrders");

        //This is our third endpoint in the Readme, using a processor and a Pojo instead of a message
        from("cxf:bean:personEndpointPojo")
                .routeId("cxf:bean:personEndpointPojo")
                .process(new PojoProcessor())
                .log("headers ${headers}") //If we look at this log, in particular at the header "ProvaNome", we'll see that it now contains the name specified in our soap request
                .setBody(constant("OK"))
                .convertBodyTo(java.lang.String.class)
                .to("seda:incomingOrders");


        from("seda:incomingOrders")
                .routeId("seda:incomingOrders")
                .to("mock:end");


    }
}
