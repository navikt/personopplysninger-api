package no.nav.personopplysninger.integrationtests.personalia;

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.KodeverkStubs.*;
import static no.nav.personopplysninger.stubs.Norg2Stubs.stubNorg2_200;
import static no.nav.personopplysninger.stubs.Norg2Stubs.stubNorg2_500;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl200;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts500;
import static no.nav.personopplysninger.stubs.TpsStubs.stubTps200;
import static no.nav.personopplysninger.stubs.TpsStubs.stubTps500;
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
        stubTps200();
        stubNorg2_200();
    }

    @Test
    void personaliaSkalGi200MedGyldigToken() {
        ResponseEntity<PersonaliaOgAdresser> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                PersonaliaOgAdresser.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void personaliaSkalGi200MedFeilIKallMotNorg2() {
        stubNorg2_500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void personaliaSkalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void personaliaSkalGi500MedFeilIKallMotSts() {
        stubSts500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void personaliaSkalGi500MedFeilIKallMotPdl() {
        stubPdl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void personaliaSkalGi500MedFeilIKallMotTps() {
        stubTps500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void personaliaSkalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkKommuner500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/personalia",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}