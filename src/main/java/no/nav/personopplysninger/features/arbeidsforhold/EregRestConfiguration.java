package no.nav.personopplysninger.features.arbeidsforhold;

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
public class EregRestConfiguration {

    @Value("${PERSONOPPLYSNINGER-API-EREG-API-APIKEY_USERNAME}")
    private String eregApiUsername;

    @Value("${PERSONOPPLYSNINGER-API-EREG-API-APIKEY_PASSWORD}")
    private String eregApiPassword;

    @Bean
    public EregConsumer eregConsumer(
            @Named("eregClient") Client client,
            @Value("${EREG_API_URL}") String eregServiceUri) throws URISyntaxException {
        return new EregConsumer(client, new URI(eregServiceUri));
    }

    @Bean
    public Client eregClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        Client c =  ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(OidcClientRequestFilter.class)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(eregApiUsername, eregApiPassword))
                .build();
        return c;
    }

}
