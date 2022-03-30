
package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.consumer.kodeverk.domain.KodeOgTekstDto;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkPostnummer200;
import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkPostnummer500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentPostnummerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubKodeverkPostnummer200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<KodeOgTekstDto[]> response = restTemplate.exchange(
                "/postnummer",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                KodeOgTekstDto[].class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/postnummer",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkPostnummer500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/postnummer",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}