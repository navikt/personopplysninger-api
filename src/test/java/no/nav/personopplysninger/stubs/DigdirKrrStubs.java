package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class DigdirKrrStubs {

    private DigdirKrrStubs() {
        // noop
    }

    public static void stubDigdirKrr200() {
        stubFor(get(urlEqualTo("/digdir-krr-proxy/rest/v1/person"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("digdir-krr-proxy.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubDigdirKrr500() {
        stubFor(get(urlEqualTo("/digdir-krr-proxy/rest/v1/person"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubDigdirKrr404() {
        stubFor(get(urlEqualTo("/digdir-krr-proxy/rest/v1/person"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Not found")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
