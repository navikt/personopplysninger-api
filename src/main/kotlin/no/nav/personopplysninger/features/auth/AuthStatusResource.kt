package no.nav.personopplysninger.features.auth

import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

private const val claimsIssuer = "selvbetjening"

@Component
@Path("name")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class AuthStatusResource  @Autowired constructor(
        private var tpsProxyNameConsumer: TpsProxyNameConsumer
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getName(): String {
        val navn = tpsProxyNameConsumer.hentNavn(fnr)
        return """{"name":"${navn.fulltNavn()}"}"""
    }

    private inline val fnr: String get() {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject }
}
