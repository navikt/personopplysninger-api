package no.nav.personopplysninger.features.medl

import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class MedlResource @Autowired constructor(
        private val medlService: MedlService
) {

    @GET
    @Path("/hentMedlemskap")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentMedlemskap(): Response {
        return Response
                .ok(medlService.hentMeldemskap(hentFnrFraToken()))
                .build()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}
