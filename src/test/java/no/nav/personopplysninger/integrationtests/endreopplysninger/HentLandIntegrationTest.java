
package no.nav.personopplysninger.integrationtests.endreopplysninger;

import com.github.tomakehurst.wiremock.client.WireMock;
import no.nav.personopplysninger.integrationtests.AbstractIntegrationTest;
import no.nav.personopplysninger.oppslag.kodeverk.api.KodeOgTekstDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkLandkoder200;
import static no.nav.personopplysninger.stubs.KodeverkStubs.stubKodeverkLandkoder500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentLandIntegrationTest extends AbstractIntegrationTest {

    @Test
    void skalGi200MedGyldigToken() {
        stubKodeverkLandkoder200();

        ResponseEntity<KodeOgTekstDto[]> response = restTemplate.exchange(
                "/land",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                KodeOgTekstDto[].class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/land",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilIKallMotKodeverk() {
        stubKodeverkLandkoder500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/land",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);
        System.out.println(WireMock.listAllStubMappings().getMappings());

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}