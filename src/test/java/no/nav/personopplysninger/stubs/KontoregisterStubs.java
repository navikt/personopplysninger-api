package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class KontoregisterStubs {

    private KontoregisterStubs() {
        // noop
    }

    public static void stubHentKonto200() {
        stubFor(post(urlEqualTo("/kontoregister/kontoregister/api/navno/v1/hent-aktiv-konto"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kontoregister.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubHentKonto500() {
        stubFor(post(urlEqualTo("/kontoregister/kontoregister/api/navno/v1/hent-aktiv-konto"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubOppdaterKonto200() {
        stubFor(post(urlEqualTo("/kontoregister/kontoregister/api/navno/v1/oppdater-konto"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubOppdaterKonto500() {
        stubFor(post(urlEqualTo("/kontoregister/kontoregister/api/navno/v1/oppdater-konto"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
