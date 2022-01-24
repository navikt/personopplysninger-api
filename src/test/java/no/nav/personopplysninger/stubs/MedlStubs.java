package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MedlStubs {

    private MedlStubs() {
        // noop
    }

    public static void stubMedl200() {
        stubFor(get(urlEqualTo("/medl/api/v1/innsyn/person"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("medl-response.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubMedl500() {
        stubFor(get(urlEqualTo("/medl/api/v1/innsyn/person"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
