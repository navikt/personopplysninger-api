package no.nav.personopplysninger.features.kodeverk;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.personopplysninger.features.personalia.PersonConsumer;
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

    @Bean
    public KjonnConsumer kjonnConsumer(
            @Named("kjonnClient") Client client,
            @Value("${kodeverk.ws.url}") String kjonnServiceUri) throws URISyntaxException {
        return new KjonnConsumer(client, new URI(kjonnServiceUri));
    }

    @Bean
    public Client kjonnClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .build();
    }

}
