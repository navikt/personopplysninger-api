package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.RestConfiguration;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestConfiguration extends RestConfiguration {

    public TestRestConfiguration() {

        register(TestTokenGeneratorResource.class);

    }

}
