package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.RestResourceConfiguration;
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server;

@EnableMockOAuth2Server
public class TestRestResourceConfiguration extends RestResourceConfiguration {

    public TestRestResourceConfiguration() {

    }

}
