package no.nav.personopplysninger.features.personalia

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
@Path("/personalia")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = arrayOf("acr=Level4"))
class PersonaliaResource @Autowired constructor(
        private var personaliaService: PersonaliaService // TODO Are: @Inject istedenfor @Autowire? val istedenfor var?
) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hent")
    fun hentPersonalia(): Response {
        val fodselsnr = hentFnrFraToken()
        val personaliaOgAdresser = personaliaService.hentPersoninfo(fodselsnr)
        return Response
                .ok(personaliaOgAdresser)
                .build()
    }


    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }

}
