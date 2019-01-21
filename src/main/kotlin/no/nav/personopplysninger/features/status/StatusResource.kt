package no.nav.personopplysninger.features.status

import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.api.Unprotected
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

import javax.ws.rs.GET
import javax.ws.rs.Path

@Component
@Path("status")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class StatusResource {

    val isAlive: String
        @GET
        @Path("/status/isAlive")
        @Unprotected
        get() = "Ok"

    @GET
    @Path("ping")
    fun ping(): String {
        return "pong"
    }
}
