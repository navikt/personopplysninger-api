package no.nav.personopplysninger.features.sts;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

public class STSConsumer {

    private Client client;
    private URI endpoint;

    public STSConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    public String getFSSToken() {

        Invocation.Builder request = buildSTSRequest();
        return getToken(request);

    }

    private Invocation.Builder getBuilder(String path) {
        return client.target(endpoint)
                .path(path)
                .queryParam("grant_type", "client_credentials")
                .queryParam("scope", "openid")
                .request();
    }

    private Invocation.Builder buildSTSRequest() {
        return getBuilder("");
    }


    private String getToken(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readEntity(String.class, response);
        } catch (Exception e) {
            String msg = "Forsøkte å hente STSToken. endpoint=[" + endpoint + "].";
        }
        return null;
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new RuntimeException("Prosesseringsfeil på responsobjekt. Responsklasse: " + e.getStackTrace() + " " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }
}
