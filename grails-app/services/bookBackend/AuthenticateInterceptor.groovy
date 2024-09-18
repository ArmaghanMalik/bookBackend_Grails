package bookBackend

import org.apache.cxf.binding.soap.SoapMessage
import org.apache.cxf.phase.AbstractPhaseInterceptor
import org.apache.cxf.phase.Phase
import org.apache.cxf.headers.Header
import org.apache.cxf.interceptor.Fault
import org.w3c.dom.Element

class AuthenticateInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    AuthenticateInterceptor() {
        super(Phase.PRE_PROTOCOL)
    }

    String expectedUsername = 'admin'
    String expectedPassword = 'admin'

    @Override
    void handleMessage(SoapMessage message) throws Fault {

        String providedUsername = null
        String providedPassword = null

        def headers = message.getHeaders()
        if (!headers || headers.isEmpty()) {
            throw new Fault(new RuntimeException("UnAuthorized! No Headers Found!"))
        }

        headers.each { Header header ->
            if (header.object instanceof Element) {
                Element element = (Element) header.object
                String localName = element.getLocalName()

                if ("username".equalsIgnoreCase(localName)) {
                    providedUsername = element.getTextContent().toString().trim()
                } else if ("password".equalsIgnoreCase(localName)) {
                    providedPassword = element.getTextContent().toString().trim()
                }
            }
        }

        // Check if the credentials match the expected ones
        if (!authenticate(providedUsername, providedPassword)) {
            throw new Fault(new RuntimeException("UnAuthorized!"))
        }
    }

    boolean authenticate(String providedUsername, String providedPassword) {
        return providedUsername == expectedUsername && providedPassword == expectedPassword
    }
}
