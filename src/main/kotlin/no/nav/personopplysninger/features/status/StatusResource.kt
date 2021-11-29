package no.nav.personopplysninger.features.status

import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path

@Component
@Path("internal")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = ["acr=Level4"])
class StatusResource {

    @GET
    @Path("isAlive")
    @Unprotected
    fun isAlive(): String {
        return "Ok"
    }

    @GET
    @Path("ping")
    fun ping(): String {
        return "pong"
    }
}
