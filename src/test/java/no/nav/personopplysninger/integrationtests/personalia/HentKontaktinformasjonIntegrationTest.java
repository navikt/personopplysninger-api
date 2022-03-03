package no.nav.personopplysninger.integrationtests.personalia;

import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.DigdirKrrStubs.*;
import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkSpraak200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

class HentKontaktinformasjonIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubDigdirKrr200();
        stubKodeverkSpraak200();
    }

    @Test
    void kontaktinformasjonSkalGi200MedGyldigToken() {
        ResponseEntity<Kontaktinformasjon> response = restTemplate.exchange(
                "/kontaktinformasjon",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                Kontaktinformasjon.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void kontaktinformasjonSkalGiTomResponsMedClientErrorIKallMotDigdir() {
        stubDigdirKrr404();

        ResponseEntity<Kontaktinformasjon> response = restTemplate.exchange(
                "/kontaktinformasjon",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                Kontaktinformasjon.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));

        Kontaktinformasjon body = response.getBody();
        assert body != null;

        assertThat(body.getEpostadresse(), is(emptyOrNullString()));
        assertThat(body.getKanVarsles(), is(nullValue()));
        assertThat(body.getMobiltelefonnummer(), is(emptyOrNullString()));
        assertThat(body.getReservert(), is(nullValue()));
        assertThat(body.getSpraak(), is(emptyOrNullString()));
    }

    @Test
    void kontaktinformasjonSkalGiSkalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/kontaktinformasjon",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void kontaktinformasjonSkalGi500MedServerErrorIKallMotDigdir() {
        stubDigdirKrr500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/kontaktinformasjon",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}