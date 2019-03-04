package no.nav.personopplysninger.features.personalia;

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk;
import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException;
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

    public DigitalKontaktinfoBolk hentKontaktinformasjon(String[] fnr) {
        Invocation.Builder request = buildRequest(fnr);
        return hentKontaktinformasjon(request);
    }

    private Invocation.Builder buildRequest(String[] fnr) {
        return client.target(endpoint)
                .path("personer/kontaktinformasjon")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CORRELATION_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personidenter", fnr);
    }


    private DigitalKontaktinfoBolk hentKontaktinformasjon(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }


    private DigitalKontaktinfoBolk readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten DKIF. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(DigitalKontaktinfoBolk.class, r);
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
