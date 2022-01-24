package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StsStubs {

    private StsStubs() {
        // noop
    }

    public static void stubSts200() {
        stubFor(get(urlEqualTo("/sts?grant_type=client_credentials&scope=openid"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("token.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubSts500() {
        stubFor(get(urlEqualTo("/sts?grant_type=client_credentials&scope=openid"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
