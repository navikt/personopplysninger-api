package no.nav.personopplysninger.features.norg2;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter;
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
public class Norg2RestConfiguration {

    @Value("${PERSONOPPLYSNINGER-API-NORG2_API_V1-APIKEY_USERNAME}")
    private String norg2ApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER-API-NORG2_API_V1-APIKEY_PASSWORD}")
    private String norg2ApiKeyPassword;

    @Bean
    public Norg2Consumer norg2Consumer(
            @Named("norg2Client") Client client,
            @Value("${NORG2_REST_API_URL}") String norg2ServiceUri) throws URISyntaxException {
        return new Norg2Consumer(client, new URI(norg2ServiceUri));
    }

    @Bean
    public Client norg2Client(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(norg2ApiKeyUsername, norg2ApiKeyPassword))
                .build();
    }

}
