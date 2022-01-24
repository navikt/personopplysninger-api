package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Norg2Stubs {

    private Norg2Stubs() {
        // noop
    }

    public static void stubNorg2_200() {
        stubFor(get(urlEqualTo("/norg2/enhet/navkontor/460108"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("norg2-navkontor.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlEqualTo("/norg2/enhet/1203/kontaktinformasjon"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("norg2-kontaktinformasjon.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubNorg2_500() {
        stubFor(get(urlEqualTo("/norg2/enhet/navkontor/460108"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlEqualTo("/norg2/enhet/1203/kontaktinformasjon"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
