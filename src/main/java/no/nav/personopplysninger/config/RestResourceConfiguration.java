package no.nav.personopplysninger.config;

import no.nav.personopplysninger.features.endreopplysninger.EndreOpplysningerResource;
import no.nav.personopplysninger.features.featuretoggles.FeatureTogglesResource;
import no.nav.personopplysninger.features.personalia.PersonaliaResource;
import no.nav.personopplysninger.features.status.StatusResource;
import no.nav.security.oidc.jaxrs.OidcContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.processor.OptionsMethodProcessor;
import org.glassfish.jersey.server.wadl.processor.WadlModelProcessor;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RestResourceConfiguration extends ResourceConfig {
    private static final List<Class> WHITELISTED_CLASSES = Arrays.asList(WadlModelProcessor.OptionsHandler.class, FeatureTogglesResource.class);
    private static final List<Class> WHITELISTED_PARENT_CLASSES = Arrays.asList(OptionsMethodProcessor.class);

    public RestResourceConfiguration() {
        register(JacksonFeature.class);
        ResourceConfig register = register (StatusResource.class);
        register(PersonaliaResource.class);
        register(FeatureTogglesResource.class);
        register(CORSResponseFilter.class);
        //register(OidcContainerRequestFilter.class);
        register(OidcResourceFilteringFeature.class);
        register(EndreOpplysningerResource.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
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
