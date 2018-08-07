package no.nav.personopplysninger_backend.api;

import no.nav.personopplysninger_backend.config.RestConfiguration;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration() {

        register(TestTokenGeneratorResource.class);

    }

}
