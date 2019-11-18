package no.nav.personopplysninger.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import no.nav.personopplysninger.config.RestClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.List;

public class ConsumerFactory {
    private static final Logger log = LoggerFactory.getLogger(ConsumerFactory.class);

    public static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    public static final String DEFAULT_APIKEY_USERNAME = "x-nav-apiKey";
    public static final String HEADER_NAV_CONSUMER_ID = "Nav-Consumer-Id";
    public static final String HEADER_NAV_CALL_ID = "Nav-Call-Id";
    public static final String HEADER_NAV_PERSONIDENT_KEY = "Nav-Personident";

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

    public static <T> T readEntityAsString(Class<T> responsklasse, Response response) {
        String json = "";
        try {
            json = response.readEntity(String.class);
            return new RestClientConfiguration()
                    .clientObjectMapperResolver().getContext(responsklasse).readValue(json, responsklasse);
        } catch (Exception e) {
            log.error("Feilet med json:\n ".concat(json));
            throw new ConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }

    public static <T> List<T> readEntities(Class<T> responsklasse, Response response) {
        return readEntities(responsklasse, response.readEntity(String.class));
    }

    public static <T> List<T> readEntities(Class<T> responsklasse, String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, responsklasse);
            return mapper.readValue(json, type);
        } catch (ProcessingException e) {
            throw new ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: ", e);
        } catch (IllegalStateException e) {
            throw new ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: ", e);
        } catch (Exception e) {
            throw new ConsumerException("Uventet feil på responsobjektet. Responsklasse: ", e);
        }
    }

}
