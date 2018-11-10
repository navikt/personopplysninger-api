package no.nav.personopplysninger.config;

import no.nav.personopplysninger.features.personalia.PersonaliaResource;
import no.nav.personopplysninger.features.status.StatusResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestResourceConfiguration extends ResourceConfig {


    public RestResourceConfiguration() {
        register(JacksonFeature.class);
        register(StatusResource.class);
        register(PersonaliaResource.class);
        register(OidcContainerRequestFilter.class);
    }

}
