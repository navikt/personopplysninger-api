package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.KontaktadresseType.*
import no.nav.personopplysninger.features.personalia.pdl.PdlKontaktadresseObjectMother
import no.nav.personopplysninger.features.personalia.tps.PersonaliaKodeverkObjectMother
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktadresseTranformerTest {

    @Test
    fun canTransformVegdresse() {
        val inbound = PdlKontaktadresseObjectMother.dummyVegadresse()

        val personaliaKodeverk = PersonaliaKodeverkObjectMother.dummyStedOgLandKode()

        val actual = KontaktadresseTranformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.type, VEGADRESSE)

        val vegadresse = actual as Vegadresse

        assertEquals(vegadresse.husnummer, inbound.vegadresse?.husnummer)
        assertEquals(vegadresse.husbokstav, inbound.vegadresse?.husbokstav)
        assertEquals(vegadresse.bruksenhetsnummer, inbound.vegadresse?.bruksenhetsnummer)
        assertEquals(vegadresse.adressenavn, inbound.vegadresse?.adressenavn)
        assertEquals(vegadresse.kommunenummer, inbound.vegadresse?.kommunenummer)
        assertEquals(vegadresse.tilleggsnavn, inbound.vegadresse?.tilleggsnavn)
        assertEquals(vegadresse.postnummer, inbound.vegadresse?.postnummer)
        assertEquals(vegadresse.poststed, personaliaKodeverk.kontaktadressePostSted)
        assertEquals(vegadresse.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(vegadresse.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(vegadresse.coAdressenavn, inbound.coAdressenavn)
    }

    @Test
    fun canTransformPostadresseIFrittFormat() {
        val inbound = PdlKontaktadresseObjectMother.dummyPostadresseIFrittFormat()

        val personaliaKodeverk = PersonaliaKodeverkObjectMother.dummyStedOgLandKode()

        val actual = KontaktadresseTranformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.type, POSTADRESSE_I_FRITT_FORMAT)

        val postAdresseIFrittFormat = actual as PostAdresseIFrittFormat

        assertEquals(postAdresseIFrittFormat.adresselinje1,inbound.postadresseIFrittFormat?.adresselinje1)
        assertEquals(postAdresseIFrittFormat.adresselinje2,inbound.postadresseIFrittFormat?.adresselinje2)
        assertEquals(postAdresseIFrittFormat.adresselinje3,inbound.postadresseIFrittFormat?.adresselinje3)
        assertEquals(postAdresseIFrittFormat.postnummer,inbound.postadresseIFrittFormat?.postnummer)
        assertEquals(postAdresseIFrittFormat.poststed, personaliaKodeverk.kontaktadressePostSted)
        assertEquals(postAdresseIFrittFormat.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(postAdresseIFrittFormat.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(postAdresseIFrittFormat.coAdressenavn, inbound.coAdressenavn)
    }

    @Test
    fun canTransformPdlPostboksadresse() {
        val inbound = PdlKontaktadresseObjectMother.dummyPostboksadresse()

        val personaliaKodeverk = PersonaliaKodeverkObjectMother.dummyStedOgLandKode()

        val actual = KontaktadresseTranformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.type, POSTBOKSADRESSE)

        val postboksadresse = actual as Postboksadresse

        assertEquals(postboksadresse.postbokseier,inbound.postboksadresse?.postbokseier)
        assertEquals(postboksadresse.postboks,inbound.postboksadresse?.postboks)
        assertEquals(postboksadresse.postnummer,inbound.postboksadresse?.postnummer)
        assertEquals(postboksadresse.poststed, personaliaKodeverk.kontaktadressePostSted)
        assertEquals(postboksadresse.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(postboksadresse.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(postboksadresse.coAdressenavn, inbound.coAdressenavn)
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = PdlKontaktadresseObjectMother.dummyUtenlandskAdresse()

        val personaliaKodeverk = PersonaliaKodeverkObjectMother.dummyStedOgLandKode()

        val actual = KontaktadresseTranformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.type, UTENLANDSK_ADRESSE)

        val utenlandskAdresse = actual as UtenlandskAdresse

        assertEquals(utenlandskAdresse.adressenavnNummer, inbound.utenlandskAdresse?.adressenavnNummer)
        assertEquals(utenlandskAdresse.bygningEtasjeLeilighet, inbound.utenlandskAdresse?.bygningEtasjeLeilighet)
        assertEquals(utenlandskAdresse.postboksNummerNavn, inbound.utenlandskAdresse?.postboksNummerNavn)
        assertEquals(utenlandskAdresse.postkode, inbound.utenlandskAdresse?.postkode)
        assertEquals(utenlandskAdresse.bySted, inbound.utenlandskAdresse?.bySted)
        assertEquals(utenlandskAdresse.regionDistriktOmraade, inbound.utenlandskAdresse?.regionDistriktOmraade)
        assertEquals(utenlandskAdresse.landkode, inbound.utenlandskAdresse?.landkode)
        assertEquals(utenlandskAdresse.land, personaliaKodeverk.kontaktadresseLand)
        assertEquals(utenlandskAdresse.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(utenlandskAdresse.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(utenlandskAdresse.coAdressenavn, inbound.coAdressenavn)
    }

    @Test
    fun canTransformUtenlandskAdresseIFrittFormat() {
        val inbound = PdlKontaktadresseObjectMother.dummyUtenlandskAdresseIFrittFormat()

        val personaliaKodeverk = PersonaliaKodeverkObjectMother.dummyStedOgLandKode()

        val actual = KontaktadresseTranformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.type, UTENLANDSK_ADRESSE_I_FRITT_FORMAT)

        val utenlandskAdresseIFrittFormat = actual as UtenlandskAdresseIFrittFormat

        assertEquals(utenlandskAdresseIFrittFormat.adresselinje1, inbound.utenlandskAdresseIFrittFormat?.adresselinje1)
        assertEquals(utenlandskAdresseIFrittFormat.adresselinje2, inbound.utenlandskAdresseIFrittFormat?.adresselinje2)
        assertEquals(utenlandskAdresseIFrittFormat.adresselinje3, inbound.utenlandskAdresseIFrittFormat?.adresselinje3)
        assertEquals(utenlandskAdresseIFrittFormat.postkode, inbound.utenlandskAdresseIFrittFormat?.postkode)
        assertEquals(utenlandskAdresseIFrittFormat.byEllerStedsnavn, inbound.utenlandskAdresseIFrittFormat?.byEllerStedsnavn)
        assertEquals(utenlandskAdresseIFrittFormat.landkode, inbound.utenlandskAdresseIFrittFormat?.landkode)
        assertEquals(utenlandskAdresseIFrittFormat.land, personaliaKodeverk.kontaktadresseLand)
        assertEquals(utenlandskAdresseIFrittFormat.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(utenlandskAdresseIFrittFormat.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(utenlandskAdresseIFrittFormat.coAdressenavn, inbound.coAdressenavn)
    }
}