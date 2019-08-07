package no.nav.personopplysninger.features.personalia;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.ConsumerFactory;
import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException;
import no.nav.tps.person.Personinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class PersonConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersonConsumer.class);

    private Client client;
    private URI endpoint;


    public PersonConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public Personinfo hentPersonInfo(String fnr) {
        Invocation.Builder request = buildRequest(fnr);
        return hentPersoninfo(request);
    }


    private Invocation.Builder buildRequest(String fnr) {
        return client.target(endpoint)
                .path("person")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
                .header("Nav-Personident", fnr);
    }


    private Personinfo hentPersoninfo(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (ConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "].";
            throw new ConsumerException(msg, e);
        }
    }


    private Personinfo readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten TPS-proxy. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new ConsumerException(msg + " - " + ConsumerFactory.readEntity(String.class, r));
        } else {
            return ConsumerFactory.readEntity(Personinfo.class, r);
        }
    }

}
