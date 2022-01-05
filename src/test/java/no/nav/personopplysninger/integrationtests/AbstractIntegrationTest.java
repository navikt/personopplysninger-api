package no.nav.personopplysninger.integrationtests;

import com.github.tomakehurst.wiremock.WireMockServer;
import no.nav.personopplysninger.api.TestLauncher;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

@ActiveProfiles({"itest", "dev"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestLauncher.class})
@AutoConfigureWireMock(port = 0)
public abstract class AbstractIntegrationTest {

    // TokenX needs to run on its own wiremock server, as it is used in application config which is run before
    // the autoconfigured wiremock server is started.
    static WireMockServer tokenxMockServer = new WireMockServer(findAvailableTcpPort());

    @BeforeAll
    static void setup() {
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
}
