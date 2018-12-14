package no.nav.personopplysninger.features.personalia

import no.nav.security.oidc.api.ProtectedWithClaims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.springframework.stereotype.Controller

@Component
@Path("/personalia")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class PersonaliaResource @Autowired constructor(
        private var personaliaService : PersonaliaService
){

    private val SELVBETJENING = "selvbetjening"

    val hentPersonalia: Response
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/hent")
        get() {
            val fodselsnr = hentFnrFraToken()
            val personInfo = personaliaService.hentPersoninfo(fodselsnr)
            return Response
                    .ok(personInfo)
                    .build()
        }

    fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(SELVBETJENING).claimSet.subject
    }

}