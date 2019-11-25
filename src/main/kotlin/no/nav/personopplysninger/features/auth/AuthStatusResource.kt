package no.nav.personopplysninger.features.auth

import no.nav.security.oidc.api.Unprotected
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Context

@Component
@Path("auth")
class AuthStatusResource {
    @GET
    @Unprotected
    fun isAuthenticatedWithOIDC(@Context request: HttpServletRequest): String {
        return request.cookies?.any { cookie -> cookie.name.equals("selvbetjening-idtoken") }.toString().toLowerCase()
    }
}
