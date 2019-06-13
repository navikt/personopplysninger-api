package no.nav.personopplysninger.features.featuretoggles

import no.nav.personopplysninger.features.personalia.PersonaliaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import no.nav.sbl.featuretoggle.unleash.UnleashService;
import no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig;
import javax.ws.rs.core.MediaType
import javax.inject.Provider;
import no.finn.unleash.UnleashContext;
import kotlin.collections.Map;
import kotlin.collections.toMap;
import javax.ws.rs.core.Response
import no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig.UNLEASH_API_URL_PROPERTY_NAME
import no.nav.sbl.util.EnvironmentUtils.getOptionalProperty
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context

@Component
@Path("/")
class FeatureTogglesResource @Autowired constructor() {

    @GET
    @Path("/feature-toggles")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentFeatureToggles( @Context request: HttpServletRequest, @QueryParam("feature") features : List<String>): Response {

        System.out.println(features);

        var unleashService = unleashService(Provider { request });
        val unleashContext = UnleashContext.builder()
                .userId("test")
                .sessionId("test")
                .remoteAddress(request.getRemoteAddr())
                .build()

        val evaluation: Map<String, Boolean> = features.map{
            feature -> feature to unleashService.isEnabled(feature, unleashContext)
        }.toMap()

        return Response
                .ok(evaluation)
                .build()
    }

    fun unleashService(httpServletRequestProvider: Provider<HttpServletRequest>): UnleashService {
        return UnleashService(UnleashServiceConfig.builder()
                .applicationName(System.getenv("APPLICATION_NAME"))
                .unleashApiUrl(getOptionalProperty(UNLEASH_API_URL_PROPERTY_NAME).orElse("https://unleash.nais.adeo.no/api/"))
                .build(),
                ByQueryParamStrategy(httpServletRequestProvider),
                ByApplicationStrategy()
        )
    }
}
