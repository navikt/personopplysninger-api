package no.nav.personopplysninger.features.kodeverk;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.config.ApplicationConfig;
import no.nav.personopplysninger.features.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.kodeverk.GetKodeverkKoderBetydningerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class KodeverkConsumer {

    private static final Logger log = LoggerFactory.getLogger(KodeverkConsumer.class);

    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    private Client client;
    private URI endpoint;



    public KodeverkConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public GetKodeverkKoderBetydningerResponse hentKjonn(String kode) {
        Invocation.Builder request = buildRequest(kode);
        return hentKjonn(request);
    }


    private Invocation.Builder buildRequest(String kode) {
        return client.target(endpoint)
                .path("v1/kodeverk/Kjønnstyper/koder/betydninger")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }


    private GetKodeverkKoderBetydningerResponse hentKjonn(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (KodeverkConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "].";
            throw new KodeverkConsumerException(msg, e);
        }
    }

    private GetKodeverkKoderBetydningerResponse readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new KodeverkConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(GetKodeverkKoderBetydningerResponse.class, r);
        }
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            log.warn("Respons string " + response.toString());
            log.warn("Respons " + responsklasse.getName());
            log.warn("Respons Entity " + response.getEntity().toString());
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new KodeverkConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new KodeverkConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new KodeverkConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }
}