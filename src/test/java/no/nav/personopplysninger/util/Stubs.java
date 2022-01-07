package no.nav.personopplysninger.util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Stubs {

    private Stubs() {
        // noop
    }

    public static void stubTps() {
        stubFor(get(urlEqualTo("/tpsproxy/person"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("tps.json")));
    }

    public static void stubPdl() {
        stubFor(post(urlEqualTo("/pdl/graphql"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("pdl.json")));
    }

    public static void stubSts() {
        stubFor(get(urlEqualTo("/sts?grant_type=client_credentials&scope=openid"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("token.json")));
    }

    public static void stubKodeverk() {
        stubFor(get(urlEqualTo("/kodeverk/v1/kodeverk/Kommuner/koder/betydninger?spraak=nb&ekskluderUgyldige=false"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-kommuner.json")));

        stubFor(get(urlEqualTo("/kodeverk/v1/kodeverk/Landkoder/koder/betydninger?spraak=nb&ekskluderUgyldige=true"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-land.json")));

        stubFor(get(urlEqualTo("/kodeverk/v1/kodeverk/StatsborgerskapFreg/koder/betydninger?spraak=nb&ekskluderUgyldige=true"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-statsborgerskap.json")));

        stubFor(get(urlEqualTo("/kodeverk/v1/kodeverk/Valutaer/koder/betydninger?spraak=nb&ekskluderUgyldige=true"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-valutaer.json")));

        stubFor(get(urlEqualTo("/kodeverk/v1/kodeverk/Postnummer/koder/betydninger?spraak=nb&ekskluderUgyldige=true"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-postnummer.json")));
    }

    public static void stubNorg2() {
        stubFor(get(urlEqualTo("/norg2/enhet/navkontor/460108"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("norg2-navkontor.json")));

        stubFor(get(urlEqualTo("/norg2/enhet/1203/kontaktinformasjon"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("norg2-kontaktinformasjon.json")));
    }
}
