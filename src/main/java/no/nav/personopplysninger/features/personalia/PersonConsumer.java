package no.nav.personopplysninger.features.personalia;

import no.nav.log.MDCConstants;
import no.nav.tps.person.Personinfo;
import org.slf4j.MDC;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class PersonConsumer {

    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    private Client client;
    private URI endpoint;


    public PersonConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public Personinfo hentPersonInfo(String fnr) {
        Invocation.Builder request = buildRequest(fnr);
        return hentPersoninfo(request);
    }


    private Invocation.Builder buildRequest(String fnr) {
        return client.target(endpoint)
                .path("person")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CORRELATION_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", fnr);
    }


    private Personinfo hentPersoninfo(Invocation.Builder request) {
        try {
            try (Response response = request.get()) {
                return readResponse(response);
            }
        } catch (TpsProxyConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "].";
            throw new TpsProxyConsumerException(msg, e);
        }
    }


    private Personinfo readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new TpsProxyConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(Personinfo.class, r);
        }
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new TpsProxyConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new TpsProxyConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new TpsProxyConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }


}
