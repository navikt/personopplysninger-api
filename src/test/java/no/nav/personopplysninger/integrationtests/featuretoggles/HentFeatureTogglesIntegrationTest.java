package no.nav.personopplysninger.integrationtests.featuretoggles;

import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentFeatureTogglesIntegrationTest extends AbstractIntegrationTest {

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<Map<String, Boolean>> response = restTemplate.exchange(
                "/feature-toggles?feature=personopplysninger.pdl&feature=personopplysninger.dsop&feature=personopplysninger.inst&feature=personopplysninger.skatt&feature=personopplysninger.medl&feature=personopplysninger.fullmakt&feature=personopplysninger.tilrettelegging&feature=pdl-fullmakt",
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
}