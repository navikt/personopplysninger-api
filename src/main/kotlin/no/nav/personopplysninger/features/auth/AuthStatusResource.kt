package no.nav.personopplysninger.features.auth

import no.nav.personopplysninger.features.personalia.pdl.PdlConsumer
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.jaxrs.JaxrsTokenValidationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.isEmpty
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

private const val claimsIssuer = "selvbetjening"

@Component
@Path("name")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class AuthStatusResource  @Autowired constructor(private var pdlConsumer: PdlConsumer) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getName(): Map<String, String> {
        val navn = pdlConsumer.getNavn(fnr).navn.firstOrNull()
        val fulltNavn = listOf(navn?.fornavn, navn?.mellomnavn, navn?.etternavn).filter { !isEmpty(it) }
            .joinToString(separator = " ")
        return mapOf("name" to fulltNavn)
    }

    private inline val fnr: String get() {
        val context = JaxrsTokenValidationContextHolder.getHolder()
        return context.tokenValidationContext.getClaims(claimsIssuer).subject
    }
}
