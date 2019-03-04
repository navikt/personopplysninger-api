package no.nav.personopplysninger.features.personalia;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.config.ApplicationConfig;
import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException;
import no.nav.tps.person.Personinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class KontaktinfoConsumer {
    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    private Client client;
    private URI endpoint;


    public KontaktinfoConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public Personinfo hentKontaktinfo(String[] fnr) {
        Invocation.Builder request = buildRequest(fnr);
        return hentKontaktinfo(request);
    }

    private Invocation.Builder buildRequest(String[] fnr) {
        return client.target(endpoint)
                .path("kontaktinformasjon")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CORRELATION_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personidenter", fnr);
    }


    private Personinfo hentKontaktinfo(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }


    private Personinfo readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(Personinfo.class, r);
        }
    }

    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new ConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }
}
