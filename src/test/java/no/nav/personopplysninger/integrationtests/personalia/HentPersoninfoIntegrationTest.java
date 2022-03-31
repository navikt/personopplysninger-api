package no.nav.personopplysninger.integrationtests.personalia;

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser;
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static no.nav.personopplysninger.stubs.KodeverkStubs.*;
import static no.nav.personopplysninger.stubs.KontoregisterStubs.stubHentKonto200;
import static no.nav.personopplysninger.stubs.KontoregisterStubs.stubHentKonto500;
import static no.nav.personopplysninger.stubs.Norg2Stubs.stubNorg2_200;
import static no.nav.personopplysninger.stubs.Norg2Stubs.stubNorg2_500;
import static no.nav.personopplysninger.stubs.PdlStubs.*;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentPersoninfoIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubKodeverkKommuner200();
        stubKodeverkLandkoder200();
        stubKodeverkStatsborgerskapFreg200();
        stubKodeverkValutaer200();
        stubKodeverkPostnummer200();
        stubKodeverkKommuner200();
        stubKodeverkKommuner200();
        stubKodeverkKommuner200();
        stubSts200();
        stubPdl200();
        stubHentKonto200();
        stubNorg2_200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<PersonaliaOgAdresser> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                PersonaliaOgAdresser.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi200MedFeilIKallMotNorg2() {
        stubNorg2_500();

        ResponseEntity<PersonaliaOgAdresser> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                PersonaliaOgAdresser.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi200MedOppholdAnnetSted() {
        stubPdl200OppholdAnnetSted();

        ResponseEntity<PersonaliaOgAdresser> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                PersonaliaOgAdresser.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi200MedToKontaktadresserOgOppholdsadresser() {
        stubPdl200FlereAdresser();

        ResponseEntity<PersonaliaOgAdresser> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                PersonaliaOgAdresser.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));

        Adresser adresser = Objects.requireNonNull(response.getBody()).getAdresser();

        assert adresser != null;
        assertThat(adresser.getKontaktadresser().size(), is(2));
        assertThat(adresser.getOppholdsadresser().size(), is(2));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotPdl() {
        stubPdl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotKontoregister() {
        stubHentKonto500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkKommuner500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}