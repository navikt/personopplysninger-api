package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.consumer.pdlmottak.dto.Telefonnummer
import no.nav.personopplysninger.features.endreopplysninger.dto.Kontonummer
import no.nav.personopplysninger.util.hentFnrFraToken
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class EndreOpplysningerResource @Autowired constructor(private var endreOpplysningerService: EndreOpplysningerService) {

    @POST
    @Path("/endreTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(
            hentFnrFraToken(), telefonnummer
        )
        return Response.ok(resp).build()
    }

    @POST
    @Path("/slettTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun slettTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.slettTelefonNummer(
            hentFnrFraToken(), telefonnummer
        )
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreKontonummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreKontonummer(kontonummer: Kontonummer): Response {
        endreOpplysningerService.endreKontonummer(
            hentFnrFraToken(), kontonummer
        )
        return Response.ok(mapOf("statusType" to "OK")).build()
    }

    @POST
    @Path("/slettKontaktadresse")
    @Produces(MediaType.APPLICATION_JSON)
    fun slettKontaktadresse(): Response {
        val resp = endreOpplysningerService.slettKontaktadresse(hentFnrFraToken())
        return Response.ok(resp).build()
    }

    @GET
    @Path("/retningsnumre")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentRetningsnummer(): Response {
        val retningsnumre = endreOpplysningerService.hentRetningsnumre()
        return Response.ok(retningsnumre).build()
    }

    @GET
    @Path("/land")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentLand(): Response {
        return Response.ok(endreOpplysningerService.hentLand()).build()
    }

    @GET
    @Path("/valuta")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentValuta(): Response {
        return Response.ok(endreOpplysningerService.hentValuta()).build()
    }

    @GET
    @Path("/postnummer")
    @Produces(MediaType.APPLICATION_JSON)
    fun hentpostnummer(): Response {
        return Response.ok(endreOpplysningerService.hentPostnummer()).build()
    }
}
