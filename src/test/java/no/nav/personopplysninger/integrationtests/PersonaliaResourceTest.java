package no.nav.personopplysninger.integrationtests;

import com.github.tomakehurst.wiremock.client.WireMock;
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.util.Stubs.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class PersonaliaResourceTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubSts();
        stubPdl();
        stubTps();
        stubKodeverk();
        stubNorg2();
    }

    @AfterEach
    void teardown() {
        WireMock.reset();
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
}