package no.nav.personopplysninger.features.endreopplysninger;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerException;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;
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

    public String oppdaterTelefonnummer(String fnr, Integer nyttNummer, String systemUserToken) {
        Telefonnummer telefonnummer = new Telefonnummer("kilde", "+47", nyttNummer, "MOBIL");
        Invocation.Builder request = buildOppdaterTelefonnummerRequest(fnr, systemUserToken);
        return sendOppdateringTelefonnummer(request, telefonnummer, systemUserToken);
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
        return getBuilder("endring/telefonnummer", systemUserToken)
                .header("Nav-Personident", fnr);
    }

    private Invocation.Builder buildPollEndringRequest(String url, String systemUserToken) {
        return getBuilder(url.replaceAll("/api/v1/", ""), systemUserToken);
    }

    private String sendOppdateringTelefonnummer(Invocation.Builder request, Telefonnummer telefonnummer, String systemUserToken) {
        try (Response response = request.post(Entity.entity(telefonnummer, MediaType.APPLICATION_JSON))) {
            return readResponseAndPollStatus(response, systemUserToken);
        }
        catch (Exception e) {
            String msg = "Forsøkte å oppdatere telefonnummer. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private String readResponseAndPollStatus(Response r, String systemUserToken) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere person_mottak. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            String pollEndringUrl = r.getHeaderString(HttpHeaders.LOCATION);
            Response response = buildPollEndringRequest(pollEndringUrl, systemUserToken).get();
            Endring endring = readEntity(Endring.class, response);
            int i = 0;
            while (endring.isPending() && i < MAX_POLLS) {
                i++;
                try {
                    Thread.sleep(SLEEP_TIME_MS);
                } catch (InterruptedException ie) {
                    throw new ConsumerException("Fikk feil under polling på status", ie);
                }
                response = buildPollEndringRequest(pollEndringUrl, systemUserToken).get();
                endring = readEntity(Endring.class, response);
            }
            return endring.getEndringstype();
        }
    }

}
