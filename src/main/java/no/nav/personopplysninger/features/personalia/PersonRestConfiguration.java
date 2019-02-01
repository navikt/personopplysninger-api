package no.nav.personopplysninger.features.personalia;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.security.oidc.jaxrs.OidcClientRequestFilter;
import org.glassfish.jersey.logging.LoggingFeature;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class PersonRestConfiguration {

    @Value("${tpsProxyUsername}")
    private String tpsProxyApiKeyUsername;

    @Value("${tpsProxyUsername}")
    private String tpsProxyApiKeyPassword;

    @Bean
    public PersonConsumer personConsumer(
            @Named("tpsProxyClient") Client client,
            @Value("${tpsInnsynURL}") String personServiceUri) throws URISyntaxException {
        return new PersonConsumer(client, new URI(personServiceUri));
    }

    @Bean
    public Client tpsProxyClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(tpsProxyApiKeyUsername, tpsProxyApiKeyPassword))
                .build();
    }

}
