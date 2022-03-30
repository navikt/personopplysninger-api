package no.nav.personopplysninger.features.auth

import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.util.hentFnrFraToken
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.apache.commons.lang3.StringUtils.isEmpty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

private const val claimsIssuer = "selvbetjening"

@Component
@Path("name")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class AuthStatusResource @Autowired constructor(private var pdlConsumer: PdlConsumer) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getName(): Map<String, String> {
        val navn = pdlConsumer.getNavn(hentFnrFraToken()).navn.firstOrNull()
        val fulltNavn = listOf(navn?.fornavn, navn?.mellomnavn, navn?.etternavn).filter { !isEmpty(it) }
            .joinToString(separator = " ")
        return mapOf("name" to fulltNavn)
    }
}
