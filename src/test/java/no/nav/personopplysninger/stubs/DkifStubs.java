package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class DkifStubs {

    private DkifStubs() {
        // noop
    }

    public static void stubDkif200() {
        stubFor(get(urlEqualTo("/dkif/v1/personer/kontaktinformasjon"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("dkif.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubDkif500() {
        stubFor(get(urlEqualTo("/dkif/v1/personer/kontaktinformasjon"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
