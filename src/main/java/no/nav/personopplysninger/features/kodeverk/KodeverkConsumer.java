package no.nav.personopplysninger.features.kodeverk;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse;
import no.nav.personopplysninger.features.kodeverk.exceptions.KodeverkConsumerException;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.personopplysninger.features.ConsumerFactory.readEntity;

public class KodeverkConsumer {
    
    private Client client;
    private URI endpoint;
    private static final String SPRAAK = "nb";

    public KodeverkConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    @Cacheable("retningsnummer")
    public GetKodeverkKoderBetydningerResponse hentRetningsnumre() {
        Invocation.Builder request = buildRetningsnummerRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("kjonn")
    public GetKodeverkKoderBetydningerResponse hentKjonn() {
        Invocation.Builder request = buildKjonnstyperRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("kommune")
    public GetKodeverkKoderBetydningerResponse hentKommuner() {
        Invocation.Builder request = buildKommuneRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("land")
    public GetKodeverkKoderBetydningerResponse hentLandKoder() {
        Invocation.Builder request = buildLandkoderRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("status")
    public GetKodeverkKoderBetydningerResponse hentPersonstatus() {
        Invocation.Builder request = buildPersonstatusRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("postnr")
    public GetKodeverkKoderBetydningerResponse hentPostnummer() {
        Invocation.Builder request = buildPostnummerRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("sivilstand")
    public GetKodeverkKoderBetydningerResponse hentSivilstand() {
        Invocation.Builder request = buildSivilstandRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("spraak")
    public GetKodeverkKoderBetydningerResponse hentSpraak() {
        Invocation.Builder request = buildSpraakRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("valuta")
    public GetKodeverkKoderBetydningerResponse hentValuta() {
        Invocation.Builder request = buildValutaRequest();
        return hentKodeverkBetydning(request);
    }

    @Cacheable("statsborgerskap")
    public GetKodeverkKoderBetydningerResponse hentStatsborgerskap() {
        Invocation.Builder request = buildStatsborgerskapRequest();
        return hentKodeverkBetydning(request);
    }

    private Invocation.Builder getBuilder(String path, Boolean eksluderUgyldige) {
        return client.target(endpoint)
                .path(path)
                .queryParam("spraak", SPRAAK)
                .queryParam("ekskluderUgyldige", eksluderUgyldige)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID);
    }

    private Invocation.Builder buildRetningsnummerRequest() {
        return getBuilder("v1/kodeverk/Retningsnumre/koder/betydninger", true);
    }

    private Invocation.Builder buildKjonnstyperRequest() {
        return getBuilder("v1/kodeverk/Kjønnstyper/koder/betydninger", true);
    }

    private Invocation.Builder buildKommuneRequest() {
        return getBuilder("v1/kodeverk/Kommuner/koder/betydninger", false);
    }

    private Invocation.Builder buildLandkoderRequest() {
        return getBuilder("v1/kodeverk/Landkoder/koder/betydninger", false);
    }

    private Invocation.Builder buildPersonstatusRequest() {
        return getBuilder("v1/kodeverk/Personstatuser/koder/betydninger", true);
    }

    private Invocation.Builder buildPostnummerRequest() {
        return getBuilder("v1/kodeverk/Postnummer/koder/betydninger", true);
    }

    private Invocation.Builder buildSivilstandRequest() {
        return getBuilder("v1/kodeverk/Sivilstander/koder/betydninger", true);
    }

    private Invocation.Builder buildSpraakRequest() {
        return getBuilder("v1/kodeverk/Språk/koder/betydninger", true);
    }

    private Invocation.Builder buildValutaRequest() {
        return getBuilder("v1/kodeverk/Valutaer/koder/betydninger", true);
    }

    private Invocation.Builder buildStatsborgerskapRequest() {
        return getBuilder("v1/kodeverk/StatsborgerskapFreg/koder/betydninger", true);
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

}
