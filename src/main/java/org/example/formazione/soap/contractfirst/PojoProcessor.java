package org.example.formazione.soap.contractfirst;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.message.MessageContentsList;
import org.example.formazione.soap.contractfirst.InputPerson;

public class PojoProcessor implements Processor {
    //If we're getting any "errors" here it's normale, the IDE isnt' recognizing InputPerson because it's generated
    //If you're on Intellij, just click on "Generate Sources and update folders for all projects" on the Maven toolbar on the right
    @Override
    public void process(Exchange exchange) throws Exception {
        MessageContentsList inputPersonIngresso = (MessageContentsList) exchange.getIn().getBody(); //We know that CXF "wraps" our request inside a MessageContentsList
        InputPerson inputPerson = (InputPerson) inputPersonIngresso.get(0); //Here we are letting CXF know that we're expecting a InputPerson class inside is MessageContentsList
        exchange.getIn().setHeader("ProvaNome", inputPerson.getFirstName()); //Setting our header so we can see it later in logs
    }
}
