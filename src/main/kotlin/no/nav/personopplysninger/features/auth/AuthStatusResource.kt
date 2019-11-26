package no.nav.personopplysninger.features.auth

import no.nav.security.oidc.api.Unprotected
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

@Component
@Path("auth")
class AuthStatusResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Unprotected
    fun isAuthenticatedWithOIDC(@Context request: HttpServletRequest): String {
        return """{"authenticated":"${request.cookies?.any { cookie -> cookie.name.equals("selvbetjening-idtoken") }.toString().toLowerCase()}"}"""
    }
}
