package no.nav.personopplysninger.features.arbeidsforhold;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.arbeidsforhold.exceptions.EregConsumerException;
import no.nav.personopplysninger.features.ereg.EregOrganisasjon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

public class EregConsumer {

    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    private Client client;
    private URI endpoint;
    private static final Logger log = LoggerFactory.getLogger(EregConsumer.class);


    public EregConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }


    public EregOrganisasjon hentOrgnavn(String orgnr) {
        Invocation.Builder request = buildOrgnrRequest(orgnr);
        return hentOrganisasjonsNavn(request);
    }

    private Invocation.Builder buildOrgnrRequest(String orgnr) {

        return client.target(endpoint)
                .path("v1/organisasjon/" + orgnr)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID);

    }


    private EregOrganisasjon hentOrganisasjonsNavn(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (EregConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere REST-tjenesten Enhetsregisteret. endpoint=[" + endpoint + "].";
            throw new EregConsumerException(msg, e);
        }
    }

    private EregOrganisasjon readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere REST-tjenesten Enhetsregister. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new EregConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            EregOrganisasjon orgnr = readEntity(EregOrganisasjon.class, r);
            return orgnr;
        }
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new EregConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new EregConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new EregConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }


}
