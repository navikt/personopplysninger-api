package no.nav.personopplysninger_backend.api.rest.person

import com.google.gson.GsonBuilder
import no.nav.personopplysninger_backend.modell.person.mock.PersonInformasjonMock
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
        get() =  gson.toJson(PersonInformasjonMock().mockPersonInformasjon())
}