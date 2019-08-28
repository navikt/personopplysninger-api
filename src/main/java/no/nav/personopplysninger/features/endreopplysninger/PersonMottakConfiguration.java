package no.nav.personopplysninger.features.endreopplysninger;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.personopplysninger.features.norg2.Norg2Consumer;
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.ContextResolver;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class PersonMottakConfiguration {

    @Value("${PERSONOPPLYSNINGER_API_PERSON_MOTTAK_API_V1_APIKEY_USERNAME}")
    private String personMottakApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER_API_PERSON_MOTTAK_API_V1_APIKEY_PASSWORD}")
    private String personMottakApiKeyPassword;

    @Bean
    public PersonMottakConsumer personMottakConsumer(
            @Named("personMottakClient") Client client,
            @Value("${PERSON_MOTTAK_API_V1_URL}") String personMottakServiceUri) throws URISyntaxException {
        return new PersonMottakConsumer(client, new URI(personMottakServiceUri));
    }

    @Bean
    public Client personMottakClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(personMottakApiKeyUsername, personMottakApiKeyPassword))
                .build();
    }

}
