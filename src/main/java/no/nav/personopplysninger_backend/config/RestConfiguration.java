package no.nav.personopplysninger_backend.config;

import no.nav.personopplysninger_backend.api.rest.StatusResource;
import no.nav.personopplysninger_backend.api.rest.person.PersonInformasjonResurs;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfiguration extends ResourceConfig {


    public RestConfiguration() {

        register(JacksonFeature.class);
        register(StatusResource.class);
        register(PersonInformasjonResurs.class);
        register(OidcContainerRequestFilter.class);

    }

}
