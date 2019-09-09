package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.EndringUtenlandsadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Gateadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Postboksadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Utenlandsadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
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
                hentFnrFraToken(), telefonnummer, HttpMethod.POST)
        return Response.ok(resp).build()
    }

    @POST
    @Path("/slettTelefonnummer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun slettTelefonnummer(telefonnummer: Telefonnummer): Response {
        val resp = endreOpplysningerService.endreTelefonnummer(
                hentFnrFraToken(), telefonnummer, HttpMethod.PUT)
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

    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}