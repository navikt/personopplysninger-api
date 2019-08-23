package no.nav.personopplysninger.features.endreopplysninger;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerException;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.endreopplysninger.api.OppdaterTelefonnumerResponse;
import no.nav.personopplysninger.features.endreopplysninger.domain.TelefonnummerDto;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.personopplysninger.features.ConsumerFactory.readEntity;

public class PersonMottakConsumer {

    private static final String BEARER = "Bearer ";

    private Client client;
    private URI endpoint;

    public PersonMottakConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public OppdaterTelefonnumerResponse oppdaterTelefonnummer(String fnr, Integer nyttNummer, String systemUserToken) {
        TelefonnummerDto telefonnummerDto = new TelefonnummerDto("kilde", "+47", nyttNummer, "MOBIL");
        Invocation.Builder request = buildOppdaterTelefonnummerRequest(fnr, systemUserToken);
        return sendOppdateringTelefonnummer(request, telefonnummerDto);
    }

    private Invocation.Builder getBuilder(String fnr, String path, String systemUserToken) {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Token", BEARER.concat(systemUserToken))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("Nav-Personident", fnr);
    }

    private Invocation.Builder buildOppdaterTelefonnummerRequest(String fnr, String systemUserToken) {
        return getBuilder(fnr, "api/v1/endring/telefonnummer", systemUserToken);

    }

    private OppdaterTelefonnumerResponse sendOppdateringTelefonnummer(Invocation.Builder request, TelefonnummerDto telefonnummerDto) {
        try (Response response = request.post(Entity.entity(telefonnummerDto, MediaType.APPLICATION_JSON))) {
            return readResponseBetydning(response);
        }
        catch (Exception e) {
            String msg = "Forsøkte å oppdatere telefonnummer. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }

    private OppdaterTelefonnumerResponse readResponseBetydning(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere person_mottak. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(OppdaterTelefonnumerResponse.class, r);
        }
    }

}
