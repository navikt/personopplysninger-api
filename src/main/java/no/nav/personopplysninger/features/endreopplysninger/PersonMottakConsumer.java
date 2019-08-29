package no.nav.personopplysninger.features.endreopplysninger;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerException;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.endreopplysninger.domain.EndringTelefon;
import no.nav.personopplysninger.features.endreopplysninger.domain.Telefonnummer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

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

    private Client client;
    private URI endpoint;

    public PersonMottakConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public EndringTelefon endreTelefonnummer(String fnr, Telefonnummer telefonnummer, String systemUserToken, String httpMethod) {
        Invocation.Builder request = buildOppdaterTelefonnummerRequest(fnr, systemUserToken);
        return sendEndringTelefonnummer(request, telefonnummer, systemUserToken, httpMethod);
    }

    private Invocation.Builder getBuilder(String path, String systemUserToken) {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Token", BEARER.concat(systemUserToken))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID);
    }

    private Invocation.Builder buildOppdaterTelefonnummerRequest(String fnr, String systemUserToken) {
        return getBuilder("/api/v1/endring/telefonnummer", systemUserToken)
                .header("Nav-Personident", fnr);
    }

    private Invocation.Builder buildPollEndringRequest(String url, String systemUserToken) {
        return getBuilder(url, systemUserToken);
    }

    private EndringTelefon sendEndringTelefonnummer(Invocation.Builder request, Telefonnummer telefonnummer, String systemUserToken, String httpMethod) {
        try (Response response = request.method(httpMethod, Entity.entity(telefonnummer, MediaType.APPLICATION_JSON))) {
            return readResponseAndPollStatus(response, systemUserToken);
        }
        catch (Exception e) {
            String msg = "Forsøkte å endre telefonnummer. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private EndringTelefon readResponseAndPollStatus(Response response, String systemUserToken) {
        if (!SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere person_mottak. endpoint=[" + endpoint + "], HTTP response status=[" + response.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, response));
        } else {
            String pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION);
            EndringTelefon endring = null;
            int i = 0;
            do {
                try {
                    Thread.sleep(SLEEP_TIME_MS);
                } catch (InterruptedException ie) {
                    throw new ConsumerException("Fikk feil under polling på status", ie);
                }
                Response pollResponse = buildPollEndringRequest(pollEndringUrl, systemUserToken).get();
                endring = readEntity(EndringTelefon.class, pollResponse);
            } while (++i < MAX_POLLS && endring.isPending());
            log.info("Antall polls for status: " + i);
            return endring;
        }
    }
}
