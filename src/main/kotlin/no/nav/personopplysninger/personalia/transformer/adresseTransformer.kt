package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse

private const val FANT_IKKE_ADRESSE_ERROR_MESSAGE = "Fant ikke adresse som skulle transformeres"


fun transformVegadresse(inbound: no.nav.pdl.generated.dto.hentpersonquery.Vegadresse?, poststed: String?, kommune: String?): Vegadresse {
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
    inbound: PostadresseIFrittFormat?,
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

fun transformPostboksadresse(inbound: no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse?, poststed: String?): Postboksadresse {
    inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
    return Postboksadresse(
        postbokseier = inbound.postbokseier,
        postboks = inbound.postboks,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformUtenlandskAdresse(
    inbound: no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse?,
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
    inbound: no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat?,
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
    inbound: no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse?,
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
