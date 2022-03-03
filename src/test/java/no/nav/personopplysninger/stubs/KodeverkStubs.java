package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class KodeverkStubs {

    private KodeverkStubs() {
        // noop
    }

    public static void stubKodeverkKommuner200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Kommuner/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-kommuner.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkLandkoder200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Landkoder/.*"))
                .atPriority(2)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-land.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkStatsborgerskapFreg200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/StatsborgerskapFreg/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-statsborgerskap.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkValutaer200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Valutaer/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-valutaer.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkPostnummer200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Postnummer/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-postnummer.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkGrunnlagMedl200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/GrunnlagMedl/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-grunnlagmedl.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkDekningMedl200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/DekningMedl/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-dekningmedl.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkRetningsnumre200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Retningsnumre/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-retningsnumre.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkSpraak200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Spr%C3%A5k/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-spraak.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkKommuner500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Kommuner/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkLandkoder500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Landkoder/.*"))
                .atPriority(1)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkStatsborgerskapFreg500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/StatsborgerskapFreg/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkValutaer500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Valutaer/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkPostnummer500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Postnummer/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkGrunnlagMedl500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/GrunnlagMedl/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkDekningMedl500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/DekningMedl/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubKodeverkRetningsnumre500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Retningsnumre/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
