package no.nav.personopplysninger.features.auth

import no.nav.security.oidc.api.ProtectedWithClaims
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Component
@Path("name")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class AuthStatusResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getName(): String {
        return """{"name":""}"""
    }
}
