package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.pdl.dto.adresse.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.*

fun transformVegadresse(inbound: PdlVegadresse, poststed: String?, kommune: String?): Vegadresse {
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
    inbound: PdlPostadresseIFrittFormat,
    poststed: String?
): PostAdresseIFrittFormat {
    return PostAdresseIFrittFormat(
        adresselinje1 = inbound.adresselinje1,
        adresselinje2 = inbound.adresselinje2,
        adresselinje3 = inbound.adresselinje3,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformPostboksadresse(inbound: PdlPostboksadresse, poststed: String?): Postboksadresse {
    return Postboksadresse(
        postbokseier = inbound.postbokseier,
        postboks = inbound.postboks,
        postnummer = inbound.postnummer,
        poststed = poststed,
    )
}

fun transformUtenlandskAdresse(
    inbound: PdlUtenlandskAdresse,
    land: String?
): UtenlandskAdresse {
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
    inbound: PdlUtenlandskAdresseIFrittFormat,
    land: String?
): UtenlandskAdresseIFrittFormat {
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
    inbound: PdlMatrikkeladresse,
    poststed: String?,
    kommune: String?
): Matrikkeladresse {
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
