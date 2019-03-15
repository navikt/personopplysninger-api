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
public class PersonaliaRestConfiguration {

    @Value("${PERSONOPPLYSNINGER-API-TPS-PROXY_API_V1_INNSYN-APIKEY_USERNAME}")
    private String tpsProxyApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER-API-TPS-PROXY_API_V1_INNSYN-APIKEY_PASSWORD}")
    private String tpsProxyApiKeyPassword;

    @Value("${PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_USERNAME}")
    private String dkifApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_PASSWORD}")
    private String dkifApiKeyPassword;

    @Bean
    public PersonConsumer personConsumer(
            @Named("tpsProxyClient") Client client,
            @Value("${TPS_PROXY_API_V1_INNSYN_URL}") String personServiceUri) throws URISyntaxException {
        return new PersonConsumer(client, new URI(personServiceUri));
    }

    @Bean
    public KontaktinfoConsumer kontaktinformasjonConsumer(
            @Named("dkifClient") Client client,
            @Value("${DKIF_API_URL}") String kontaktinfoServiceUri) throws URISyntaxException {
        return new KontaktinfoConsumer(client, new URI(kontaktinfoServiceUri));
    }

    @Bean
    public Client tpsProxyClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return clientBuilder(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(tpsProxyApiKeyUsername, tpsProxyApiKeyPassword))
                .build();
    }

    @Bean
    public Client dkifClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return clientBuilder(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(dkifApiKeyUsername, dkifApiKeyPassword))
                .build();
    }

    private ClientBuilder clientBuilder(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE));
    }
}
