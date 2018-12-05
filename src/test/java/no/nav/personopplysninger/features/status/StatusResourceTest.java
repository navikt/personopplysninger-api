package no.nav.personopplysninger.features.status;

import com.nimbusds.jwt.SignedJWT;
import no.nav.personopplysninger.api.TestLauncher;
import no.nav.security.oidc.OIDCConstants;
import no.nav.security.oidc.test.support.JwtTokenGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { TestLauncher.class })
public class StatusResourceTest {

    @Value("${local.server.port}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Test
    public void skalGi200MedGyldigToken() {

        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        SignedJWT signedJWT = JwtTokenGenerator.createSignedJWT("12345678911");
        Response response = target.path("/status/ping")
                .request()
                .header(OIDCConstants.AUTHORIZATION_HEADER, "Bearer " + signedJWT.serialize())
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void skalGi401UtenToken() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/status/ping")
                .request()
                .get();

        assertThat(response.getStatus(), is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200UtenHeaders() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/status/ping")
                .request()
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200WithFirefoxDefaultAcceptHeader() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/status/ping")
                .request()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

    @Test
    public void optionsSkalGi200WithChromeDefaultAcceptHeader() {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:" + port + contextPath);
        Response response = target.path("/status/ping")
                .request()
                .header("Accept", "*/*")
                .options();

        assertThat(response.getStatus(), is(equalTo(Response.Status.OK.getStatusCode())));
    }

}
