package no.nav.personopplysninger_backend.api.rest.person

import com.google.gson.GsonBuilder
import no.nav.personopplysninger_backend.modell.person.Navn
import no.nav.personopplysninger_backend.modell.person.PersonInformasjon
import no.nav.personopplysninger_backend.modell.person.Sivilstand
import no.nav.personopplysninger_backend.modell.person.Telefon
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

    fun mockPersonInformasjon(): String{
        val navn: Navn = Navn(
                "1990.01.01",
                "",
                "Prøve",
                "",
                "",
                "Kanin",
                "")

        val telefon: Telefon = Telefon(
                "12345678",
                "2010.01.01",
                "",
                "87654321",
                "2012.01.01",
                "",
                "",
                "",
                "")

        val sivilstand: Sivilstand = Sivilstand(
                "1990.01.01",
                "",
                "Ugift")

        val personinfo: PersonInformasjon = PersonInformasjon(
                foedselsdato = "1990.01.01",
                datoFraOgMed = "1990.01.01",
                ident = "01019012345",
                identtype = "fnr",
                kjonn = "Kvinne",
                navn = navn,
                sivilstand = sivilstand,
                telefon = telefon)

        return gson.toJson(personinfo);
    }

    val hentPersonInformasjon: String
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/personinfo")
        @Unprotected
        get() =  mockPersonInformasjon()
}