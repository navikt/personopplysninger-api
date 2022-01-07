package no.nav.personopplysninger.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class StatusResourceTest extends AbstractIntegrationTest {

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/internal/ping",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi401UtenToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/internal/ping",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void optionsSkalGi200UtenHeaders() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/internal/ping",
                HttpMethod.OPTIONS,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void optionsSkalGi200WithFirefoxDefaultAcceptHeader() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/internal/ping",
                HttpMethod.OPTIONS,
                createEntityWithHeaders(
                        Map.of("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                ),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void optionsSkalGi200WithChromeDefaultAcceptHeader() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/internal/ping",
                HttpMethod.OPTIONS,
                createEntityWithHeaders(Map.of("Accept", "*/*")),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }
}