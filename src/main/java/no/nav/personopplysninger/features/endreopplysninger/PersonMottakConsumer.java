package no.nav.personopplysninger.features.endreopplysninger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerException;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.EndringPostboksadresse;
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Gateadresse;
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.EndringGateadresse;
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Postboksadresse;
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer;
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.personopplysninger.features.ConsumerFactory.readEntity;

public class PersonMottakConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersonMottakConsumer.class);

    private static final String BEARER = "Bearer ";
    private static final Integer SLEEP_TIME_MS = 1000;
    private static final Integer MAX_POLLS = 3;

    private static final String URL_TELEFONNUMMER = "/api/v1/endring/telefonnummer";
    private static final String URL_KONTONUMMER = "/api/v1/endring/bankkonto";
    private static final String URL_GATEADRESSE = "/api/v1/endring/kontaktadresse/norsk/gateadresse";
    private static final String URL_POSTBOKSADRESSE = "/api/v1/endring/kontaktadresse/norsk/postboksadresse";

    private Client client;
    private URI endpoint;

    public PersonMottakConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public EndringTelefon endreTelefonnummer(String fnr, Telefonnummer telefonnummer, String systemUserToken, String httpMethod) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_TELEFONNUMMER);
        return sendEndring(request, telefonnummer, systemUserToken, httpMethod, EndringTelefon.class);
    }

    public EndringKontonummer endreKontonummer(String fnr, Kontonummer kontonummer, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_KONTONUMMER);
        return sendEndring(request, kontonummer, systemUserToken, HttpMethod.POST, EndringKontonummer.class);
    }

    public EndringGateadresse endreGateadresse(String fnr, Gateadresse gateadresse, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_GATEADRESSE);
        return sendEndring(request, gateadresse, systemUserToken, HttpMethod.POST, EndringGateadresse.class);
    }

    public EndringPostboksadresse endrePostboksadresse(String fnr, Postboksadresse postboksadresse, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_POSTBOKSADRESSE);
        return sendEndring(request, postboksadresse, systemUserToken, HttpMethod.POST, EndringPostboksadresse.class);
    }

    private Invocation.Builder getBuilder(String path, String systemUserToken) {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Token", BEARER.concat(systemUserToken))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID);
    }

    private Invocation.Builder buildEndreRequest(String fnr, String systemUserToken, String path) {
        return getBuilder(path, systemUserToken)
                .header("Nav-Personident", fnr);
    }

    private Invocation.Builder buildPollEndringRequest(String url, String systemUserToken) {
        return getBuilder(url, systemUserToken);
    }

    private <T extends Endring<T>> T sendEndring(Invocation.Builder request, Object entitetSomEndres, String systemUserToken, String httpMethod, Class<T> c) {
        try (Response response = request.method(httpMethod, Entity.entity(entitetSomEndres, MediaType.APPLICATION_JSON))) {
            return readResponseAndPollStatus(response, systemUserToken, c);
        }
        catch (Exception e) {
            String msg = "Forsøkte å endre telefonnummer. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private <T extends Endring<T>> T readResponseAndPollStatus(Response response, String systemUserToken, Class<T> c) {
        if (!SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere person_mottak. endpoint=[" + endpoint + "], HTTP response status=[" + response.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, response));
        } else {
            String pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION);
            T endring = null;
            int i = 0;
            do {
                try {
                    Thread.sleep(SLEEP_TIME_MS);
                } catch (InterruptedException ie) {
                    throw new ConsumerException("Fikk feil under polling på status", ie);
                }
                Response pollResponse = buildPollEndringRequest(pollEndringUrl, systemUserToken).get();
                String resp = readEntity(String.class, pollResponse);
                log.info("Response: ".concat(resp));
                endring = readEntity(c, pollResponse);
            } while (++i < MAX_POLLS && endring.isPending());
            log.info("Antall polls for status: " + i);
            if (!endring.isDoneDone()) {
                String msg = "";
                try {
                    msg = new  ObjectMapper().writeValueAsString(endring);
                } catch (JsonProcessingException jpe) {}
                log.warn("Endring gav ikke status Done-Done. \n".concat(msg));
            }
            return endring;
        }
    }
}
