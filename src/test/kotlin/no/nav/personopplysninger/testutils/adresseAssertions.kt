package no.nav.personopplysninger.testutils

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.*
import org.junit.jupiter.api.Assertions.assertEquals

fun assertVegadresseEquals(vegadresse: Vegadresse, poststed: String, inbound: PdlVegadresse) {
    assertEquals(vegadresse.husnummer, inbound.husnummer)
    assertEquals(vegadresse.husbokstav, inbound.husbokstav)
    assertEquals(vegadresse.bruksenhetsnummer, inbound.bruksenhetsnummer)
    assertEquals(vegadresse.adressenavn, inbound.adressenavn)
    assertEquals(vegadresse.kommunenummer, inbound.kommunenummer)
    assertEquals(vegadresse.tilleggsnavn, inbound.tilleggsnavn)
    assertEquals(vegadresse.postnummer, inbound.postnummer)
    assertEquals(vegadresse.poststed, poststed)
}

fun assertPostAdresseIFrittFormatEquals(
    postAdresseIFrittFormat: PostAdresseIFrittFormat,
    poststed: String,
    inbound: PdlPostadresseIFrittFormat
) {
    assertEquals(postAdresseIFrittFormat.adresselinje1, inbound.adresselinje1)
    assertEquals(postAdresseIFrittFormat.adresselinje2, inbound.adresselinje2)
    assertEquals(postAdresseIFrittFormat.adresselinje3, inbound.adresselinje3)
    assertEquals(postAdresseIFrittFormat.postnummer, inbound.postnummer)
    assertEquals(postAdresseIFrittFormat.poststed, poststed)

}

fun assertPostboksadresseEquals(
    postboksadresse: Postboksadresse,
    poststed: String,
    inbound: PdlPostboksadresse
) {
    assertEquals(postboksadresse.postbokseier, inbound.postbokseier)
    assertEquals(postboksadresse.postboks, inbound.postboks)
    assertEquals(postboksadresse.postnummer, inbound.postnummer)
    assertEquals(postboksadresse.poststed, poststed)
}

fun assertUtenlandskAdresseEquals(
    utenlandskAdresse: UtenlandskAdresse,
    land: String,
    inbound: PdlUtenlandskAdresse
) {
    assertEquals(utenlandskAdresse.adressenavnNummer, inbound.adressenavnNummer)
    assertEquals(utenlandskAdresse.bygningEtasjeLeilighet, inbound.bygningEtasjeLeilighet)
    assertEquals(utenlandskAdresse.postboksNummerNavn, inbound.postboksNummerNavn)
    assertEquals(utenlandskAdresse.postkode, inbound.postkode)
    assertEquals(utenlandskAdresse.bySted, inbound.bySted)
    assertEquals(utenlandskAdresse.regionDistriktOmraade, inbound.regionDistriktOmraade)
    assertEquals(utenlandskAdresse.landkode, inbound.landkode)
    assertEquals(utenlandskAdresse.land, land)
}

fun assertUtenlandskAdresseIFrittFormatEquals(
    utenlandskAdresseIFrittFormat: UtenlandskAdresseIFrittFormat,
    land: String,
    inbound: PdlUtenlandskAdresseIFrittFormat
) {
    assertEquals(utenlandskAdresseIFrittFormat.adresselinje1, inbound.adresselinje1)
    assertEquals(utenlandskAdresseIFrittFormat.adresselinje2, inbound.adresselinje2)
    assertEquals(utenlandskAdresseIFrittFormat.adresselinje3, inbound.adresselinje3)
    assertEquals(utenlandskAdresseIFrittFormat.postkode, inbound.postkode)
    assertEquals(
        utenlandskAdresseIFrittFormat.byEllerStedsnavn,
        inbound.byEllerStedsnavn
    )
    assertEquals(utenlandskAdresseIFrittFormat.landkode, inbound.landkode)
    assertEquals(utenlandskAdresseIFrittFormat.land, land)
}

