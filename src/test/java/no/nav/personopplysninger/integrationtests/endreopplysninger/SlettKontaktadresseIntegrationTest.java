package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.EndringKontaktadresse;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl200;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl500;
import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakSlettKontaktadresse200;
import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakSlettKontaktadresse500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class SlettKontaktadresseIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubPdl200();
        stubSts200();
        stubPersonmottakSlettKontaktadresse200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<EndringKontaktadresse> response = restTemplate.exchange(
                "/slettKontaktadresse",
                HttpMethod.POST,
                createEntityWithAuthHeader(IDENT),
                EndringKontaktadresse.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/slettKontaktadresse",
                HttpMethod.POST,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotPdl() {
        stubPdl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/slettKontaktadresse",
                HttpMethod.POST,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotPersonmottak() {
        stubPersonmottakSlettKontaktadresse500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/slettKontaktadresse",
                HttpMethod.POST,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}