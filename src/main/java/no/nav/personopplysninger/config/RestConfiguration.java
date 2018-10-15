package no.nav.personopplysninger.config;

import no.nav.personopplysninger.features.person.PersonResource;
import no.nav.personopplysninger.features.status.StatusResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfiguration extends ResourceConfig {


    public RestConfiguration() {

        register(JacksonFeature.class);
        register(StatusResource.class);
        register(PersonResource.class);
        register(OidcContainerRequestFilter.class);

    }

}
