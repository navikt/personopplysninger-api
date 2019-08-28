package no.nav.personopplysninger.features.endreopplysninger

import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class EndreOpplysningerResource @Autowired constructor(private var endreOpplysningerService: EndreOpplysningerService) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerResource::class.java)

    @GET
    @Path("/endreTelefonnummer/{nummer}/{landskode}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    fun endreTelefonnummer(
            @PathParam("nummer") nummer: Int,
            @PathParam("landskode") landskode: String,
            @PathParam("type") type: String): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(hentFnrFraToken(), nummer, landskode, type)
        return Response.ok(resp).build()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}