package no.nav.personopplysninger.integrationtests;

import no.nav.security.token.support.core.JwtTokenConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class StatusResourceTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Test
    public void skalGi200MedGyldigToken() {

        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        String token = createToken("12345678911");
        Response response = target.path("/internal/ping")
                .request()
                .header(JwtTokenConstants.AUTHORIZATION_HEADER, "Bearer " + token)
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void skalGi401UtenToken() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/internal/ping")
                .request()
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200UtenHeaders() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/internal/ping")
                .request()
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200WithFirefoxDefaultAcceptHeader() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/internal/ping")
                .request()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200WithChromeDefaultAcceptHeader() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/internal/ping")
                .request()
                .header("Accept", "*/*")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }
}