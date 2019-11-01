package no.nav.personopplysninger.features.endreopplysninger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerException;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;
import no.nav.personopplysninger.features.endreopplysninger.domain.Error;
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*;
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer;
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer;
import no.nav.personopplysninger.features.personalia.dto.DtoUtilsKt;
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
import static no.nav.personopplysninger.features.ConsumerFactory.readEntities;
import static no.nav.personopplysninger.features.ConsumerFactory.readEntity;

public class PersonMottakConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersonMottakConsumer.class);

    private static final int HTTP_CODE_422 = 422;
    private static final int HTTP_CODE_423 = 423;

    private static final String BEARER = "Bearer ";
    private static final Integer SLEEP_TIME_MS = 1000;
    private static final Integer MAX_POLLS = 3;

    private static final String URL_TELEFONNUMMER = "/api/v1/endring/telefonnummer";
    private static final String URL_KONTONUMMER = "/api/v1/endring/bankkonto";
    private static final String URL_GATEADRESSE = "/api/v1/endring/kontaktadresse/norsk/gateadresse";
    private static final String URL_POSTBOKSADRESSE = "/api/v1/endring/kontaktadresse/norsk/postboksadresse";
    private static final String URL_UTENLANDSADRESSE = "/api/v1/endring/kontaktadresse/utenlandsk";
    private static final String URL_STEDSADRESSE = "/api/v1/endring/kontaktadresse/norsk/stedsadresse";
    private static final String URL_OPPHOER_KONTAKTADRESSE_NORSK = "/api/v1/endring/kontaktadresse/norsk/opphoer";
    private static final String URL_OPPHOER_KONTAKTADRESSE_UTENLANDSK = "/api/v1/endring/kontaktadresse/utenlandsk/opphoer";

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

    public EndringStedsadresse endreStedsadresse(String fnr, Stedsadresse stedsadresse, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_STEDSADRESSE);
        return sendEndring(request, stedsadresse, systemUserToken, HttpMethod.POST, EndringStedsadresse.class);
    }

    public EndringPostboksadresse endrePostboksadresse(String fnr, Postboksadresse postboksadresse, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_POSTBOKSADRESSE);
        return sendEndring(request, postboksadresse, systemUserToken, HttpMethod.POST, EndringPostboksadresse.class);
    }

    public EndringUtenlandsadresse endreUtenlandsadresse(String fnr, Utenlandsadresse utenlandsadresse, String systemUserToken) {
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, URL_UTENLANDSADRESSE);
        return sendEndring(request, utenlandsadresse, systemUserToken, HttpMethod.POST, EndringUtenlandsadresse.class);
    }

    public EndringOpphoerAdresse opphoerKontaktadresse(String fnr, KontaktadresseType kontaktadresseType, String systemUserToken) {
        String url = kontaktadresseType == KontaktadresseType.NORSK ?
                URL_OPPHOER_KONTAKTADRESSE_NORSK : URL_OPPHOER_KONTAKTADRESSE_UTENLANDSK;
        Invocation.Builder request = buildEndreRequest(fnr, systemUserToken, url);
        return sendEndring(request, null, systemUserToken, HttpMethod.PUT, EndringOpphoerAdresse.class);
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
        try (Response response = entitetSomEndres == null ?
                request.method(httpMethod, Entity.text("")) :
                request.method(httpMethod, Entity.entity(entitetSomEndres, MediaType.APPLICATION_JSON))) {
            return readResponseAndPollStatus(response, systemUserToken, c);
        }
        catch (Exception e) {
            String msg = "Forsøkte å endre personopplysning. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private <T extends Endring<T>> T readResponseAndPollStatus(Response response, String systemUserToken, Class<T> c) {
        if (response.getStatus() == HTTP_CODE_423) {
            T endring = getEndring(c, "PENDING");
            endring.setError(readEntity(Error.class, response));
            log.info("Oppdatering avvist pga status pending.");
            return endring;
        } else if (response.getStatus() == HTTP_CODE_422) {
            T endring = getEndring(c, "ERROR");
            endring.setError(readEntity(Error.class, response));
            log.error("Fikk valideringsfeil: ".concat(DtoUtilsKt.getJson(endring.getError())));
            return endring;
        } else if (!SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
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
                endring = readEntities(c, pollResponse).get(0);
            } while (++i < MAX_POLLS && endring.isPending());
            log.info("Antall polls for status: " + i);

            if (!endring.isDoneWithoutTpsError()) {
                endring.createValidationErrorIfTpsHasError();
                String json = "";
                try {
                    json = new ObjectMapper().writeValueAsString(endring);
                } catch (JsonProcessingException jpe) {}
                log.warn("Endring var ikke Done og/eller hadde TPS error. \n".concat(json));
            }
            return endring;
        }
    }

    private <T extends Endring<T>> T getEndring(Class<T> c, String statusType) {
        T endring = null;
        try {
            endring = c.newInstance();
        } catch (Exception e) {
            log.error("Fikk exception ved forsøk på å instansiere " + c.getName());
            throw new RuntimeException(e);
        }
        endring.setStatusType(statusType);
        return endring;
    }
}
