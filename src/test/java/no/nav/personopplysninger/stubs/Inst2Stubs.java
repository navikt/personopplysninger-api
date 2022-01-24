package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Inst2Stubs {

    private Inst2Stubs() {
        // noop
    }

    public static void stubInst2_200() {
        stubFor(get(urlEqualTo("/inst2/v1/person/innsyn"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("inst2.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubInst2_500() {
        stubFor(get(urlEqualTo("/inst2/v1/person/innsyn"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
