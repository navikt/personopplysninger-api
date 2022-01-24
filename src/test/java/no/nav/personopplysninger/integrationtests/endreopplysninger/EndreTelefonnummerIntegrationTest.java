package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakTelefon200;
import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakTelefon500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class EndreTelefonnummerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubSts200();
        stubPersonmottakTelefon200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<EndringTelefon> response = restTemplate.exchange(
                "/endreTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer("dummy", "dummy", 1), createAuthHeader(IDENT)),
                EndringTelefon.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/endreTelefonnummer",
                HttpMethod.POST,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotSts() {
        stubSts500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/endreTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer("dummy", "dummy", 1), createAuthHeader(IDENT)),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotPersonmottak() {
        stubPersonmottakTelefon500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/endreTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer("dummy", "dummy", 1), createAuthHeader(IDENT)),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}