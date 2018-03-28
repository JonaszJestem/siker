package Siker;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.List;

public class AllegroSearcher implements Searcher {
    String key = "87d2a6ce";

    @Override
    public List<Item> search(String query) {
        try {
            MessageFactory factory = MessageFactory.newInstance(SOAPConstants.DYNAMIC_SOAP_PROTOCOL);
            SOAPMessage message = factory.createMessage();
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return null;
    }
}
