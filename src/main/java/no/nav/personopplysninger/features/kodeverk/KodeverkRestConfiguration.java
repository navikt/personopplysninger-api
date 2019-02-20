package no.nav.personopplysninger.features.kodeverk;

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
public class KodeverkRestConfiguration {

    @Value("${PERSONOPPLYSNINGER-API-KODEVERK_REST-API-APIKEY_USERNAME}")
    private String kodeverkApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER-API-KODEVERK_REST-API-APIKEY_PASSWORD}")
    private String kodeverkApiKeyPassword;

    @Bean
    public KodeverkConsumer kodeverkConsumer(
            @Named("kodeverkClient") Client client,
            @Value("${kodeverk.rest-api}") String kodeServiceUri) throws URISyntaxException {
        return new KodeverkConsumer(client, new URI(kodeServiceUri));
    }

    @Bean
    public Client kodeverkClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(kodeverkApiKeyUsername,kodeverkApiKeyPassword))
                .build();
    }

}
