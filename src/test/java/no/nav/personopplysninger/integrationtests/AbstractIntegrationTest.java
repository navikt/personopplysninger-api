package no.nav.personopplysninger.integrationtests;

import com.github.tomakehurst.wiremock.WireMockServer;
import no.nav.personopplysninger.api.TestLauncher;
import no.nav.security.mock.oauth2.MockOAuth2Server;
import no.nav.security.mock.oauth2.token.DefaultOAuth2TokenCallback;
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

@ActiveProfiles({"itest", "dev"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestLauncher.class})
@AutoConfigureWireMock(port = 0)
@EnableMockOAuth2Server
public abstract class AbstractIntegrationTest {

    private static final String CLIENT_ID = "clientId";
    private static final String ISSUER = "selvbetjening";

    protected static final String IDENT = "10108000398";

    @Value("${LOGINSERVICE_IDPORTEN_AUDIENCE}")
    String audience;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private MockOAuth2Server oAuth2Server;

    // TokenX needs to run on its own wiremock server, as it is used in application config which is run before
    // the autoconfigured wiremock server is started.
    static WireMockServer tokenxMockServer = new WireMockServer(findAvailableTcpPort());

    @BeforeAll
    static void commonSetup() {
        mockTokenX();
        mockAppName();
    }

    private static void mockAppName() {
        System.setProperty("NAIS_APP_NAME", "appname");
    }

    private static void mockTokenX() {
        tokenxMockServer.stubFor(
                get(urlMatching("/tokenx")).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withBody(
                                        "{\n" +
                                                "  \"issuer\" : \"https://dummy\",\n" +
                                                "  \"authorization_endpoint\" : \"https://dummy\",\n" +
                                                "  \"token_endpoint\" : \"https://dummy\",\n" +
                                                "  \"jwks_uri\" : \"https://dummy\",\n" +
                                                "  \"grant_types_supported\" : [ \"urn:ietf:params:oauth:grant-type:token-exchange\" ],\n" +
                                                "  \"token_endpoint_auth_methods_supported\" : [ \"private_key_jwt\" ],\n" +
                                                "  \"token_endpoint_auth_signing_alg_values_supported\" : [ \"RS256\" ],\n" +
                                                "  \"subject_types_supported\" : [ \"public\" ]\n" +
                                                "}"
                                )
                )
        );
        tokenxMockServer.start();

        System.setProperty("token.x.well.known.url", format("http://localhost:%s/tokenx", tokenxMockServer.port()));
        System.setProperty("token.x.client.id", "dev-sbs:personbruker:personopplysninger-api");
        System.setProperty("token.x.private.jwk", "{\"use\":\"sig\",\"kty\":\"RSA\",\"kid\":\"xxx\",\"n\":\"xxx\",\"e\":\"AQAB\",\"d\":\"xxx\",\"p\":\"xxx\",\"q\":\"xxx\",\"dp\":\"xxx\",\"dq\":\"xxx\",\"qi\":\"xxx\"}");
    }

    protected HttpEntity<HttpHeaders> createEntityWithHeaders(Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();
        headerMap.forEach(headers::add);
        return new HttpEntity<>(headers);
    }

    protected HttpEntity<HttpHeaders> createEntityWithAuthHeader(String subject) {
        return createEntityWithHeaders(Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + createToken(subject)));
    }

    protected HttpEntity<HttpHeaders> createEntityWithoutHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }

    private String createToken(String subject){

        return oAuth2Server.issueToken(
                ISSUER,
                CLIENT_ID,
                new DefaultOAuth2TokenCallback(
                        ISSUER,
                        subject,
                        List.of(audience),
                        Map.of("acr", "Level4"),
                        3600
                )
        ).serialize();
    }
}
