package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Gateadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.KontaktadresseType.NORSK
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.KontaktadresseType.UTENLANDSK
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Postboksadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Stedsadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Utenlandsadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.*
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class EndreOpplysningerResource @Autowired constructor(private var endreOpplysningerService: EndreOpplysningerService) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerResource::class.java)

    @POST
    @Path("/endreTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(
                hentFnrFraToken(), telefonnummer)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/slettTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun slettTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.slettTelefonNummer(
                hentFnrFraToken(), telefonnummer)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreKontonummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreKontonummer(kontonummer: Kontonummer): Response {
        val resp = endreOpplysningerService.endreKontonummer(
                hentFnrFraToken(), kontonummer)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreGateadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreGateadresse(gateadresse: Gateadresse): Response {
        val resp = endreOpplysningerService.endreGateadresse(
                hentFnrFraToken(), gateadresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreStedsadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreStedsadresse(stedsadresse: Stedsadresse): Response {
        val resp = endreOpplysningerService.endreStedsadresse(
                hentFnrFraToken(), stedsadresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endrePostboksadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endrePostboksadresse(postboksadresse: Postboksadresse): Response {
        val resp = endreOpplysningerService.endrePostboksadresse(
                hentFnrFraToken(), postboksadresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreUtenlandsadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreUtenlandsadresse(utenlandsadresse: Utenlandsadresse): Response {
        val resp = endreOpplysningerService.endreUtenlandsadresse(
                hentFnrFraToken(), utenlandsadresse)
        return Response.ok(resp).build()
    }

    @PUT
    @Path("/opphoerNorskKontaktadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun opphoerNorskKontaktadresse(): Response {
        val resp = endreOpplysningerService.opphoerAdresse(hentFnrFraToken(), NORSK)
        return Response.ok(resp).build()
    }

    @PUT
    @Path("/opphoerUtenlandskKontaktadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun opphoerUtenlandskKontaktadresse(): Response {
        val resp = endreOpplysningerService.opphoerAdresse(hentFnrFraToken(), UTENLANDSK)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreKontaktadresse/vegadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreKontaktadresseVegadresse(vegadresse: DownstreamVegadresse): Response {
        val resp = endreOpplysningerService.endreKontaktadresseVegadresse(hentFnrFraToken(), vegadresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreKontaktadresse/postboksadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreKontaktadressePostboksadresse(postboksadresse: DownstreamPostboksadresse): Response {
        val resp = endreOpplysningerService.endreKontaktadressePostboksadresse(hentFnrFraToken(), postboksadresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/endreKontaktadresse/utenlandskAdresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun endreKontaktadresseUtenlandskAdresse(utenlandskAdresse: DownstreamUtenlandskAdresse): Response {
        val resp = endreOpplysningerService.endreKontaktadresseUtenlandskAdresse(hentFnrFraToken(), utenlandskAdresse)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/slettKontaktadresse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}
