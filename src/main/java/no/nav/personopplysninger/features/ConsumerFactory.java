package no.nav.personopplysninger.features;

import no.nav.personopplysninger.features.personalia.exceptions.ConsumerException;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

public class ConsumerFactory {

    public static final String CONSUMER_ID = "personbruker-personopplysninger-api";

    public static <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new ConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }

}
