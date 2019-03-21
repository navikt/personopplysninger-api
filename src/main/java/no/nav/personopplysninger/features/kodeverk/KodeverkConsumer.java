package no.nav.personopplysninger.features.kodeverk;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse;
import no.nav.personopplysninger.features.kodeverk.exceptions.KodeverkConsumerException;
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
        Invocation.Builder request = buildKjonnstyperRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentKommuner(String kode) {
        Invocation.Builder request = buildKommuneRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentLandKoder(String kode) {
        Invocation.Builder request = buildLandkoderRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentPersonstatus(String kode) {
        Invocation.Builder request = buildPersonstatusRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentPostnummer(String kode) {
        Invocation.Builder request = buildPostnummerRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentSivilstand(String kode) {
        Invocation.Builder request = buildSivilstandRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentSpraak(String kode) {
        Invocation.Builder request = buildSpraakRequest(kode);
        return hentKodeverkBetydning(request);
    }

    public GetKodeverkKoderBetydningerResponse hentStatsborgerskap(String kode) {
        Invocation.Builder request = buildStatsborgerskapRequest(kode);
        return hentKodeverkBetydning(request);
    }

    private Invocation.Builder getBuilder(String kode, String s) {
        return client.target(endpoint)
                .path(s)
                .queryParam("spraak", "nb")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }

    private Invocation.Builder getBuilderMedUgyldigeVerdier(String kode, String s) {
        return client.target(endpoint)
                .path(s)
                .queryParam("spraak", "nb")
                .queryParam("ekskluderUgyldige", "false")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }

    private Invocation.Builder buildKjonnstyperRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Kjønnstyper/koder/betydninger");

    }

    private Invocation.Builder buildKommuneRequest(String kode) {
        return getBuilderMedUgyldigeVerdier(kode, "v1/kodeverk/Kommuner/koder/betydninger");
    }

    private Invocation.Builder buildLandkoderRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Landkoder/koder/betydninger");
    }

    private Invocation.Builder buildPersonstatusRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Personstatuser/koder/betydninger");
    }

    private Invocation.Builder buildPostnummerRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Postnummer/koder/betydninger");
    }

    private Invocation.Builder buildSivilstandRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Sivilstander/koder/betydninger");
    }

    private Invocation.Builder buildSpraakRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/Språk/koder/betydninger");
    }

    private Invocation.Builder buildStatsborgerskapRequest(String kode) {
        return getBuilder(kode, "v1/kodeverk/StatsborgerskapFreg/koder/betydninger");
    }


    private GetKodeverkKoderBetydningerResponse hentKodeverkBetydning(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponseBetydning(response);
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

    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
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
