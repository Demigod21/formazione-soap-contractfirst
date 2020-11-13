package org.example.formazione.soap.contractfirst;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.example.formazione.soap.contractfirst.InputPerson;

public class ProvaProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        MessageContentsList inputPersonIngresso = (MessageContentsList) exchange.getIn().getBody();
        InputPerson inputPerson = (InputPerson) inputPersonIngresso.get(0); //Here we are letting CXF know that we're expecting a InputPerson class inside is MessageContentsList
        exchange.getIn().setHeader("ProvaNome", inputPerson.getFirstName());
    }
}
