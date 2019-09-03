package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.Telefonnummer
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class EndreOpplysningerResource @Autowired constructor(private var endreOpplysningerService: EndreOpplysningerService) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerResource::class.java)

    @POST
    @Path("/endreTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(
                hentFnrFraToken(), telefonnummer, HttpMethod.POST)
        return Response.ok(resp).build()
    }

    @POST
    @Path("slettTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun slettTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(
                hentFnrFraToken(), telefonnummer, HttpMethod.PUT)
        return Response.ok(resp).build()
    }

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}