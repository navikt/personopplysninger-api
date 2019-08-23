package no.nav.personopplysninger.features.sts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Collections.singletonList;

@Configuration
public class STSTokenRestConfiguration {

    private final String BASIC = "BASIC ";
    private final String AUTHORIZATION = "Authorization";

    @Value("${SRVPERSONOPPLYSNINGER_API_USERNAME}")
    private String STSUsername;

    @Value("${SRVPERSONOPPLYSNINGER_API_PASSWORD}")
    private String STSPassword;

    @Value("${PERSONOPPLYSNINGER_API_SECURITY_TOKEN_SERVICE_TOKEN_APIKEY_USERNAME}")
    private String STSApiKeyUsername;

    @Value("${PERSONOPPLYSNINGER_API_SECURITY_TOKEN_SERVICE_TOKEN_APIKEY_PASSWORD}")
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
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().put(AUTHORIZATION, singletonList(getBasicAuthentication())))
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders().putSingle(STSApiKeyUsername, STSApiKeyPassword))
                .build();
        return client;
    }

}
