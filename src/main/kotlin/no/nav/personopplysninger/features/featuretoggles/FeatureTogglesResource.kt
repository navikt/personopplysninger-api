package no.nav.personopplysninger.features.featuretoggles

import no.finn.unleash.UnleashContext
import no.nav.common.utils.EnvironmentUtils.getOptionalProperty
import no.nav.sbl.featuretoggle.unleash.UnleashService
import no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig
import no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig.UNLEASH_API_URL_PROPERTY_NAME
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"
private const val UNLEASH_COOKIE_NAME = "unleash-cookie"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class FeatureTogglesResource @Autowired constructor() {
    private var unleashService = unleashService()

    @GET
    @Path("/feature-toggles")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentFeatureToggles( @Context request: HttpServletRequest, @Context response: HttpServletResponse, @CookieParam(UNLEASH_COOKIE_NAME) cookieSessionId: String?, @QueryParam("feature") features : List<String>): Response {
        try {
            val fodselsnr = hentFnrFraToken()
            val sessionId = cookieSessionId ?: generateSessionId(response)

            val unleashContext = UnleashContext.builder()
                    .userId(fodselsnr)
                    .sessionId(sessionId)
                    .remoteAddress(request.remoteAddr)
                    .build()

            val evaluation: Map<String, Boolean> = features.associateWith { feature ->
                unleashService.isEnabled(feature, unleashContext)
            }

            return Response
                    .ok(evaluation)
                    .build()
        }
        catch (error: IllegalStateException){
            error.printStackTrace()
            return Response
                    .status(403)
                    .build()
        }
    }

    private fun unleashService(): UnleashService {
        return UnleashService(UnleashServiceConfig.builder()
                .applicationName(System.getenv("NAIS_APP_NAME"))
                .unleashApiUrl(getOptionalProperty(UNLEASH_API_URL_PROPERTY_NAME).orElse("https://unleash.nais.io/api/"))
                .build(),
                ByApplicationStrategy()
        )
    }

    private fun generateSessionId(httpServletRequest: HttpServletResponse): String {
        val uuid = UUID.randomUUID()
        val sessionId = java.lang.Long.toHexString(uuid.mostSignificantBits) + java.lang.Long.toHexString(uuid.leastSignificantBits)
        val cookie = Cookie(UNLEASH_COOKIE_NAME, sessionId)
        cookie.path = "/"
        cookie.maxAge = -1
        httpServletRequest.addCookie(cookie)
        return sessionId
    }

    private fun hentFnrFraToken(): String {
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getClaims(claimsIssuer).subject
    }
}
