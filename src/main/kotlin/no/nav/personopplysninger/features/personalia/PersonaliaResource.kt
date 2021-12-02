package no.nav.personopplysninger.features.personalia

import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.CacheControl
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"
private val cacheControl = CacheControl()

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class PersonaliaResource @Autowired constructor(private var personaliaService: PersonaliaService) {

    @GET
    @Path("/personalia")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentPersoninfo(): Response {
        cacheControl.isMustRevalidate = true
        cacheControl.isNoStore = true
        val fodselsnr = hentFnrFraToken()
        val personaliaOgAdresser = personaliaService.hentPersoninfo(fodselsnr)
        return Response
                .ok(personaliaOgAdresser)
                .cacheControl(cacheControl)
                .build()
    }

    @GET
    @Path("/kontaktinformasjon")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentKontaktinformasjon(): Response {
        val fodselsnr = hentFnrFraToken()
        val kontakinformasjon = personaliaService.hentKontaktinformasjon(fodselsnr)
        return Response
                .ok(kontakinformasjon)
                .build()
    }

    private fun hentFnrFraToken(): String {
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getClaims(claimsIssuer).subject
    }

}
