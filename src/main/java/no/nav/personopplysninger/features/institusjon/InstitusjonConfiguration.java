package no.nav.personopplysninger.features.institusjon;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.personopplysninger.features.ConsumerFactory;
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
public class InstitusjonConfiguration {

    @Value("${PERSONOPPLYSNINGER_API_INST2_REST_API_APIKEY_PASSWORD}")
    private String inst2ApiKeyPassword;

    @Bean
    public InstitusjonConsumer institusjonConsumer(
            @Named("institusjonClient") Client client,
            @Value("${INST2_API_URL}") String institusjonUri) throws URISyntaxException {
        return new InstitusjonConsumer(client, new URI(institusjonUri));
    }

    @Bean
    public Client institusjonClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return clientBuilder(clientObjectMapperResolver)
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders()
                        .putSingle(ConsumerFactory.DEFAULT_APIKEY_USERNAME, inst2ApiKeyPassword))
                .build();
    }

    private ClientBuilder clientBuilder(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        return ClientBuilder.newBuilder()
                .register(OidcClientRequestFilter.class)
                .register(clientObjectMapperResolver);
    }
 }
