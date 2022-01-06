package no.nav.personopplysninger.integrationtests;

import no.nav.security.token.support.core.JwtTokenConstants;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class StatusResourceTest extends AbstractIntegrationTest {

    @Test
    void skalGi200MedGyldigToken() {
        String token = createToken("12345678911");
        Response response = createWebTarget().path("/internal/ping")
                .request()
                .header(JwtTokenConstants.AUTHORIZATION_HEADER, "Bearer " + token)
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    void skalGi401UtenToken() {
        Response response = createWebTarget().path("/internal/ping")
                .request()
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
    }

    @Test
    void optionsSkalGi200UtenHeaders() {
        Response response = createWebTarget().path("/internal/ping")
                .request()
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    void optionsSkalGi200WithFirefoxDefaultAcceptHeader() {
        Response response = createWebTarget().path("/internal/ping")
                .request()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    void optionsSkalGi200WithChromeDefaultAcceptHeader() {
        Response response = createWebTarget().path("/internal/ping")
                .request()
                .header("Accept", "*/*")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }
}