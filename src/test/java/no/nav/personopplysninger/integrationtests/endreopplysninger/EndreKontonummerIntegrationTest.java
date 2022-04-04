package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.consumer.personmottak.domain.kontonummer.EndringKontonummer;
import no.nav.personopplysninger.consumer.personmottak.domain.kontonummer.Kontonummer;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakBankkonto200;
import static no.nav.personopplysninger.stubs.PersonmottakStubs.stubPersonmottakBankkonto500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class EndreKontonummerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubSts200();
        stubPersonmottakBankkonto200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<EndringKontonummer> response = restTemplate.exchange(
                "/endreKontonummer",
                HttpMethod.POST,
                new HttpEntity<>(new Kontonummer(), createAuthHeader(IDENT)),
                EndringKontonummer.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/endreKontonummer",
                HttpMethod.POST,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotPersonmottak() {
        stubPersonmottakBankkonto500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/endreKontonummer",
                HttpMethod.POST,
                new HttpEntity<>(new Kontonummer(), createAuthHeader(IDENT)),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}