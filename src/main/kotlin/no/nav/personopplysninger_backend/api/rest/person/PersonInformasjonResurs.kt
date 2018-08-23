package no.nav.personopplysninger_backend.api.rest.person

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

    fun mockPersonInformasjon(): Map<String,Any?>{
        var personinfo = mutableMapOf<String,Any?>()
        personinfo.put("fornavn","Test")
        personinfo.put("etternavn","Testesen")
        personinfo.put("ident","12345678910")
        return personinfo;
    }

    val hentPersonInformasjon: Map<String,Any?>
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/personInformasjon")
        @Unprotected
        get() =  mockPersonInformasjon()

    val hentPerson: String
        @GET
        @Path("/person")
        @Unprotected
        get() = "Test Testesen"

}