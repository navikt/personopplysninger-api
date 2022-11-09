package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlMatrikkeladresse
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlPostadresseIFrittFormat
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlPostboksadresse
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlUtenlandskAdresse
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlUtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.common.consumer.pdl.dto.adresse.PdlVegadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse

private const val FANT_IKKE_ADRESSE_ERROR_MESSAGE = "Fant ikke adresse som skulle transformeres"

fun transformVegadresse(inbound: PdlVegadresse?, poststed: String?, kommune: String?): Vegadresse {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return Vegadresse(
        husnummer = inbound.husnummer,
        husbokstav = inbound.husbokstav,
        bruksenhetsnummer = inbound.bruksenhetsnummer,
        adressenavn = inbound.adressenavn,
        kommunenummer = inbound.kommunenummer,
        kommune = kommune,
        tilleggsnavn = inbound.tilleggsnavn,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformPostadresseIFrittFormat(
    inbound: PdlPostadresseIFrittFormat?,
    poststed: String?
): PostAdresseIFrittFormat {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return PostAdresseIFrittFormat(
        adresselinje1 = inbound.adresselinje1,
        adresselinje2 = inbound.adresselinje2,
        adresselinje3 = inbound.adresselinje3,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformPostboksadresse(inbound: PdlPostboksadresse?, poststed: String?): Postboksadresse {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return Postboksadresse(
        postbokseier = inbound.postbokseier,
        postboks = inbound.postboks,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformUtenlandskAdresse(
    inbound: PdlUtenlandskAdresse?,
    land: String?
): UtenlandskAdresse {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return UtenlandskAdresse(
        adressenavnNummer = inbound.adressenavnNummer,
        bygningEtasjeLeilighet = inbound.bygningEtasjeLeilighet,
        postboksNummerNavn = inbound.postboksNummerNavn,
        postkode = inbound.postkode,
        bySted = inbound.bySted,
        regionDistriktOmraade = inbound.regionDistriktOmraade,
        landkode = inbound.landkode,
        land = land,
    )
}

fun transformUtenlandskAdresseIFrittFormat(
    inbound: PdlUtenlandskAdresseIFrittFormat?,
    land: String?
): UtenlandskAdresseIFrittFormat {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return UtenlandskAdresseIFrittFormat(
        adresselinje1 = inbound.adresselinje1,
        adresselinje2 = inbound.adresselinje2,
        adresselinje3 = inbound.adresselinje3,
        postkode = inbound.postkode,
        byEllerStedsnavn = inbound.byEllerStedsnavn,
        landkode = inbound.landkode,
        land = land,
    )
}

fun transformMatrikkeladresse(
    inbound: PdlMatrikkeladresse?,
    poststed: String?,
    kommune: String?
): Matrikkeladresse {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return Matrikkeladresse(
        bruksenhetsnummer = inbound.bruksenhetsnummer,
        tilleggsnavn = inbound.tilleggsnavn,
        postnummer = inbound.postnummer,
        poststed = poststed,
        kommunenummer = inbound.kommunenummer,
        kommune = kommune,
    )
}

fun transformUkjentBosted(
    kommune: String?
): Ukjentbosted {
    return Ukjentbosted(
        bostedskommune = kommune,
    )
}
