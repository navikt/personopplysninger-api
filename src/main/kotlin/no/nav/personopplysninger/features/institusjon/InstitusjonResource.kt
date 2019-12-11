package no.nav.personopplysninger.features.institusjon

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
class InstitusjonResource @Autowired constructor(
        private val institusjonService: InstitusjonService
) {

    @GET
    @Path("/institusjonsopphold")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentInstitusjonsopphold(): Response {
        return Response
                .ok(institusjonService.hentInstitusjonsopphold(hentFnrFraToken()))
                .build()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}
