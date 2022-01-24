package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TpsStubs {

    private TpsStubs() {
        // noop
    }

    public static void stubTps200() {
        stubFor(get(urlEqualTo("/tpsproxy/person"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("tps.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

    }

    public static void stubTps500() {
        stubFor(get(urlEqualTo("/tpsproxy/person"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
