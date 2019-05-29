package no.nav.personopplysninger.features.norg2;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.norg2.domain.Norg2Enhet;
import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo;
import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException;
import no.nav.tps.person.Personinfo;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class Norg2Consumer {
    
    private Client client;
    private URI endpoint;

    public Norg2Consumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public Norg2Enhet hentEnhet(String geografisk) {
        Invocation.Builder request = buildEnhetRequest(geografisk, "enhet/navkontor");
        return hentEnhet(request);
    }

    public Norg2EnhetKontaktinfo hentKontaktinfo(String enhetsnr) {
        Invocation.Builder request = buildKontaktinfoRequest(enhetsnr, "enhet/kontaktinformasjon");
        return hentKontaktinfo(request);
    }

    private Invocation.Builder buildEnhetRequest(String geografisk, String path) {
        return client.target(endpoint)
                .path(path + "/" + geografisk)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID);

    }

    private Invocation.Builder buildKontaktinfoRequest(String enhetsnr, String path) {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("enhetsnr", enhetsnr);
    }

    private Norg2Enhet hentEnhet(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readEnhetResponse(response);
        } catch (ConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private Norg2EnhetKontaktinfo hentKontaktinfo(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readKontaktinfoResponse(response);
        } catch (ConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }


    private Norg2Enhet readEnhetResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + ConsumerFactory.readEntity(String.class, r));
        } else {
            return ConsumerFactory.readEntity(Norg2Enhet.class, r);
        }
    }

    private Norg2EnhetKontaktinfo readKontaktinfoResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + ConsumerFactory.readEntity(String.class, r));
        } else {
            return ConsumerFactory.readEntity(Norg2EnhetKontaktinfo.class, r);
        }
    }

}
