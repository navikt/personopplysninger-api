package no.nav.personopplysninger.features.sts;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.personopplysninger.features.arbeidsforhold.ArbeidsforholdConsumer;
import no.nav.security.oidc.OIDCConstants;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.singletonList;

@Configuration
public class STSTokenRestConfiguration {

    private final String BASIC = "BASIC ";
    private final String AUTHORIZATION = "Authorization";

   @Value("${SRVPERSONOPPLYSNINGER-API_USERNAME}")
    private String STSUsername;

    @Value("${SRVPERSONOPPLYSNINGER-API_PASSWORD}")
    private String STSPassword;

    @Value("${PERSONOPPLYSNINGER-API-SECURITY-TOKEN-SERVICE-TOKEN-APIKEY_USERNAME}")
    private String STSApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER-API-SECURITY-TOKEN-SERVICE-TOKEN-APIKEY_PASSWORD}")
    private String STSApiKeyPassword;

    @Bean
    public STSConsumer stsConsumer(
            @Named("STSClient") Client client,
            @Value("${SECURITY_TOKEN_SERVICE_TOKEN_URL}") String STSServiceUri) throws URISyntaxException {
        return new STSConsumer(client, new URI(STSServiceUri));
    }

    private String getBasicAuthentication() {
        String token = STSUsername + ":" + STSPassword;
        try {
            return BASIC + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
    }

    @Bean
    public Client STSClient(ContextResolver<ObjectMapper> clientObjectMapperResolver) {
        Client client =  ClientBuilder.newBuilder()
                .register(clientObjectMapperResolver)
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.HEADERS_ONLY, Integer.MAX_VALUE))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().put(AUTHORIZATION, singletonList(getBasicAuthentication())))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(STSApiKeyUsername, STSApiKeyPassword))
                .build();
        return client;
    }

}
