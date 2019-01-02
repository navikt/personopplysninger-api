package no.nav.personopplysninger.features.personalia;

import no.nav.log.MDCConstants;
import no.nav.tps.person.Personinfo;
import org.slf4j.MDC;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class PersonConsumer {

    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    private Client client;
    private URI endpoint;


    public PersonConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public Personinfo hentPersonInfo(String fnr) {

        Invocation.Builder request = client.target(endpoint)
                .path("person")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CORRELATION_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", fnr);

        try (Response response = request.get()) {
            if (!SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
                // TODO Are: Mer proff feilhåndtering: Logg hva ble forsøkt, hvilken URL ble brukt, fnr/fdato
                throw new RuntimeException(response.readEntity(String.class));
            } else {
                return response.readEntity(Personinfo.class);
            }
        }

    }

}
