package no.nav.personopplysninger.integrationtests.institusjon;

import no.nav.personopplysninger.consumer.inst.domain.InnsynInstitusjonsopphold;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static no.nav.personopplysninger.stubs.Inst2Stubs.stubInst2_200;
import static no.nav.personopplysninger.stubs.Inst2Stubs.stubInst2_500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentInstitusjonsoppholdIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubInst2_200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<List<InnsynInstitusjonsopphold>> response = restTemplate.exchange(
                "/institusjonsopphold",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/institusjonsopphold",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotInst2() {
        stubInst2_500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/institusjonsopphold",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}