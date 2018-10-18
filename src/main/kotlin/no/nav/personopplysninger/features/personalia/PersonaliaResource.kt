package no.nav.personopplysninger.features.personalia

import com.google.gson.GsonBuilder
import no.nav.personopplysninger.features.personalia.model.Personalia
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.api.Unprotected
import org.springframework.stereotype.Component
import javax.ws.rs.core.MediaType
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response


@Component
@Path("/personalia")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = arrayOf("acr=Level4"))
class PersonaliaResource{

    val gson = GsonBuilder().setPrettyPrinting().create()

    val hentPersonalia: Response
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/hent")
        @Unprotected
        get() {
            return Response
                    .noContent()
                    .build()
        }

    val hentPersonaliaMock: Response
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/hent-mock")
        @Unprotected
        get() {
            return Response
                    .ok(gson.toJson(Personalia("Kari", "Nordmann", "123345678911")))
                    .build()
        }
}
