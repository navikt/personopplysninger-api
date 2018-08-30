package no.nav.personopplysninger.api.rest.person

import com.google.gson.GsonBuilder
import no.nav.personopplysninger.objectmothers.person.*
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.api.Unprotected
import org.springframework.stereotype.Component
import javax.ws.rs.core.MediaType
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces


@Component
@Path("/person")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class PersonInformasjonResurs{

    val gson = GsonBuilder().setPrettyPrinting().create()

    val hentPersonInformasjon: String
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/personinfo")
        @Unprotected
        get() {
            return gson.toJson(PersonInformasjonObjectMother.getUngUgiftKvinne())
        }
}
