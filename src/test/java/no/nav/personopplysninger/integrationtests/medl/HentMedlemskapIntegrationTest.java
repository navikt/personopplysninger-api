package no.nav.personopplysninger.integrationtests.medl;

import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.KodeverkStubs.*;
import static no.nav.personopplysninger.stubs.MedlStubs.stubMedl200;
import static no.nav.personopplysninger.stubs.MedlStubs.stubMedl500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentMedlemskapIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubMedl200();
        stubKodeverkGrunnlagMedl200();
        stubKodeverkDekningMedl200();
        stubKodeverkLandkoder200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<Medlemskapsunntak> response = restTemplate.exchange(
                "/medl",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                Medlemskapsunntak.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/medl",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotMedl() {
        stubMedl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/medl",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkGrunnlagMedl500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/medl",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}