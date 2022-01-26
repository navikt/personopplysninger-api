package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PdlStubs {

    private PdlStubs() {
        // noop
    }

    public static void stubPdl200() {
        stubFor(post(urlEqualTo("/pdl/graphql"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("pdl.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubPdl200FlereAdresser() {
        stubFor(post(urlEqualTo("/pdl/graphql"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("pdl_flere_adresser.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubPdl200OppholdAnnetSted() {
        stubFor(post(urlEqualTo("/pdl/graphql"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("pdl_opphold_annet_sted.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubPdl500() {
        stubFor(post(urlEqualTo("/pdl/graphql"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
