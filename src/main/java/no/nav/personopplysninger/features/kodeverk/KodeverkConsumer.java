package no.nav.personopplysninger.features.kodeverk;

import no.nav.kodeverk.GetKodeverkKoderResponse;
import no.nav.log.MDCConstants;

import no.nav.personopplysninger.features.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.kodeverk.GetKodeverkKoderBetydningerResponse;
import no.nav.tps.person.Kode;
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

    public GetKodeverkKoderResponse hentLandKoder(Kode land) {
        Invocation.Builder request = buildRequest(land);
        return hentLandkoder(request);
    }


    private Invocation.Builder buildRequest(String kode) {
        return client.target(endpoint)
                .path("v1/kodeverk/Kjønnstyper/koder/betydninger")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }

    private Invocation.Builder buildRequest(Kode kode) {
        return client.target(endpoint)
                .path("v1/kodeverk/Landkoder/koder")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }


    private GetKodeverkKoderBetydningerResponse hentKjonn(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponseBetydning(response);
        } catch (KodeverkConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "].";
            throw new KodeverkConsumerException(msg, e);
        }
    }

    private GetKodeverkKoderResponse hentLandkoder(Invocation.Builder request) {
        try (Response response = request.get()) {
            log.warn("Koder " + response.toString());
            return readResponseKode(response);
        } catch (KodeverkConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "].";
            throw new KodeverkConsumerException(msg, e);
        }
    }

    private GetKodeverkKoderBetydningerResponse readResponseBetydning(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new KodeverkConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(GetKodeverkKoderBetydningerResponse.class, r);
        }
    }

    private GetKodeverkKoderResponse readResponseKode(Response r) {
        log.warn("KoderResponse " + r.toString());
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new KodeverkConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(GetKodeverkKoderResponse.class, r);
        }
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            log.warn("Respons string " + response.toString());
            log.warn("Respons " + responsklasse.getName());
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new KodeverkConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + e.getStackTrace() + " " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new KodeverkConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new KodeverkConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }
}