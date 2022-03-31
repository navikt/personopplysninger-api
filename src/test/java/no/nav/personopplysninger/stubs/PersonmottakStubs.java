package no.nav.personopplysninger.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PersonmottakStubs {

    private PersonmottakStubs() {
        // noop
    }

    public static void stubPersonmottakTelefon200() {
        stubEndringer200();

        stubFor(get(urlEqualTo("/personmottak/location"))
                .willReturn(aResponse()
                        .withBodyFile("endring-telefonnummer.json")
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubPersonmottakTelefon500() {
        stubEndringer500();
    }

    public static void stubPersonmottakSlettKontaktadresse200(){
        stubEndringer200();

        stubFor(get(urlEqualTo("/personmottak/location"))
                .willReturn(aResponse()
                        .withBodyFile("endring-kontaktadresse.json")
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    public static void stubPersonmottakSlettKontaktadresse500(){
        stubEndringer500();
    }

    private static void stubEndringer200() {
        stubFor(post(urlEqualTo("/personmottak/api/v1/endringer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .withHeader(HttpHeaders.LOCATION, "location")));
    }

    private static void stubEndringer500() {
        stubFor(post(urlEqualTo("/personmottak/api/v1/endringer"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
