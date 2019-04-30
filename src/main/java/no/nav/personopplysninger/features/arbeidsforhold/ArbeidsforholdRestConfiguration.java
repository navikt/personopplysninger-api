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
public class ArbeidsforholdRestConfiguration {

    @Value("${PERSONOPPLYSNINGER-API-AAREG-API-APIKEY_USERNAME}")
    private String arbeidsforholdApiUsername;

    @Value("${PERSONOPPLYSNINGER-API-AAREG-API-APIKEY_PASSWORD}")
    private String arbeidsforholdApiPassword;

    @Bean
    public ArbeidsforholdConsumer arbeidsforholdConsumer(
            @Named("arbeidsforholdClient") Client client,
            @Value("${AAREG_API_URL}") String aaregServiceUri) throws URISyntaxException {
        return new ArbeidsforholdConsumer(client, new URI(aaregServiceUri));
    }

    @Bean
    public Client arbeidsforholdClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        Client c =  ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(OidcClientRequestFilter.class)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(arbeidsforholdApiUsername, arbeidsforholdApiPassword))
                .build();
        return c;
    }

}
