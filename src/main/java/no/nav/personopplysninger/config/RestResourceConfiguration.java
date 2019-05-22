package no.nav.personopplysninger.config;

import no.nav.personopplysninger.features.personalia.ArbeidsforholdIdResource;
import no.nav.personopplysninger.features.personalia.ArbeidsforholdResource;
import no.nav.personopplysninger.features.personalia.PersonaliaResource;
import no.nav.personopplysninger.features.status.StatusResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.processor.OptionsMethodProcessor;
import org.glassfish.jersey.server.wadl.processor.WadlModelProcessor;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.util.Arrays;
import java.util.List;

public class RestResourceConfiguration extends ResourceConfig {
    private static final List<Class> WHITELISTED_CLASSES = Arrays.asList(WadlModelProcessor.OptionsHandler.class);
    private static final List<Class> WHITELISTED_PARENT_CLASSES = Arrays.asList(OptionsMethodProcessor.class);

    public RestResourceConfiguration() {
        register(JacksonFeature.class);
        register (StatusResource.class);
        register(PersonaliaResource.class);
        register(ArbeidsforholdResource.class);
        register(ArbeidsforholdIdResource.class);
        register(CORSResponseFilter.class);
        register(OidcContainerRequestFilter.class);
        register(OidcResourceFilteringFeature.class);
    }

    public static class OidcResourceFilteringFeature implements DynamicFeature {

        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            if(WHITELISTED_CLASSES.contains(resourceInfo.getResourceClass()) ||
                    WHITELISTED_PARENT_CLASSES.contains(resourceInfo.getResourceClass().getEnclosingClass())) {
                return;
            }
            context.register(OidcContainerRequestFilter.class);
        }
    }

}
