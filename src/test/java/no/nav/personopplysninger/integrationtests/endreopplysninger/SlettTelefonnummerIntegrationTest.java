package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.EndringTelefon;
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.Telefonnummer;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.PdlMottakStubs.stubPdlMottakSlettKontaktadresse500;
import static no.nav.personopplysninger.stubs.PdlMottakStubs.stubPdlMottakTelefon200;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl200;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class SlettTelefonnummerIntegrationTest extends AbstractIntegrationTest {

    private static final String LANDKODE = "+47";
    private static final String NUMMER = "55553334";
    private static final Integer PRIORITET = 1;

    @BeforeEach
    void setup() {
        stubPdl200();
        stubSts200();
        stubPdlMottakTelefon200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<EndringTelefon> response = restTemplate.exchange(
                "/slettTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer(LANDKODE, NUMMER, PRIORITET), createAuthHeader(IDENT)),
                EndringTelefon.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/slettTelefonnummer",
                HttpMethod.POST,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotPdl() {
        stubPdl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/slettTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer(LANDKODE, NUMMER, PRIORITET), createAuthHeader(IDENT)),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotPdlMottak() {
        stubPdlMottakSlettKontaktadresse500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/slettTelefonnummer",
                HttpMethod.POST,
                new HttpEntity<>(new Telefonnummer(LANDKODE, NUMMER, PRIORITET), createAuthHeader(IDENT)),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}