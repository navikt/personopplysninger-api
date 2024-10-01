package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse as PdlMatrikkeladresse
import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat as PdlPostadresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse as PdlPostboksadresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse as PdlUtenlandskAdresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat as PdlUtenlandskAdresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Vegadresse as PdlVegadresse

fun PdlVegadresse.toOutbound(poststed: String?, kommune: String?): Vegadresse {
    return Vegadresse(
        husnummer = husnummer,
        husbokstav = husbokstav,
        bruksenhetsnummer = bruksenhetsnummer,
        adressenavn = adressenavn,
        kommune = kommune,
        tilleggsnavn = tilleggsnavn,
        postnummer = postnummer,
        poststed = poststed,
    )
}

fun PdlPostadresseIFrittFormat.toOutbound(
    poststed: String?
): PostAdresseIFrittFormat {
    return PostAdresseIFrittFormat(
        adresselinje1 = adresselinje1,
        adresselinje2 = adresselinje2,
        adresselinje3 = adresselinje3,
        postnummer = postnummer,
        poststed = poststed,
    )
}

fun PdlPostboksadresse.toOutbound(poststed: String?): Postboksadresse {
    return Postboksadresse(
        postbokseier = postbokseier,
        postboks = postboks,
        postnummer = postnummer,
        poststed = poststed,
    )
}

fun PdlUtenlandskAdresse.toOutbound(
    land: String?
): UtenlandskAdresse {
    return UtenlandskAdresse(
        adressenavnNummer = adressenavnNummer,
        bygningEtasjeLeilighet = bygningEtasjeLeilighet,
        postboksNummerNavn = postboksNummerNavn,
        postkode = postkode,
        bySted = bySted,
        regionDistriktOmraade = regionDistriktOmraade,
        land = land,
    )
}

fun PdlUtenlandskAdresseIFrittFormat.toOutbound(
    land: String?
): UtenlandskAdresseIFrittFormat {
    return UtenlandskAdresseIFrittFormat(
        adresselinje1 = adresselinje1,
        adresselinje2 = adresselinje2,
        adresselinje3 = adresselinje3,
        land = land,
    )
}

fun PdlMatrikkeladresse.toOutbound(
    poststed: String?,
    kommune: String?
): Matrikkeladresse {
    return Matrikkeladresse(
        bruksenhetsnummer = bruksenhetsnummer,
        tilleggsnavn = tilleggsnavn,
        postnummer = postnummer,
        poststed = poststed,
        kommune = kommune,
    )
}