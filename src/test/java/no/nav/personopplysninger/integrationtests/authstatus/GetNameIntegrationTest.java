package no.nav.personopplysninger.integrationtests.authstatus;

import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl200;
import static no.nav.personopplysninger.stubs.PdlStubs.stubPdl500;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts200;
import static no.nav.personopplysninger.stubs.StsStubs.stubSts500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class GetNameIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubPdl200();
        stubSts200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/name",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/name",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotPdl() {
        stubPdl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/name",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotSts() {
        stubSts500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/name",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}