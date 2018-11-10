package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.RestResourceConfiguration;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestResourceConfiguration extends RestResourceConfiguration {

    public TestRestResourceConfiguration() {

        register(TestTokenGeneratorResource.class);

    }

}
