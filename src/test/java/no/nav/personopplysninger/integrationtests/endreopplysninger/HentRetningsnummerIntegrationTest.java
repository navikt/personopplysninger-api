
package no.nav.personopplysninger.integrationtests.endreopplysninger;

import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import no.nav.personopplysninger.oppslag.kodeverk.api.RetningsnummerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkRetningsnumre200;
import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkRetningsnumre500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentRetningsnummerIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubKodeverkRetningsnumre200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<RetningsnummerDTO[]> response = restTemplate.exchange(
                "/retningsnumre",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                RetningsnummerDTO[].class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/retningsnumre",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkRetningsnumre500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/retningsnumre",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}