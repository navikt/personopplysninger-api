package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse
import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse
import no.nav.pdl.generated.dto.hentpersonquery.UkjentBosted
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Vegadresse

fun createDummyVegadresse(): Vegadresse {
    return Vegadresse(
        husnummer = "dummy husnummer",
        husbokstav = "dummy husbokstav",
        bruksenhetsnummer = "dummy bruksenhetsnummer",
        adressenavn = "dummy adressenavn",
        kommunenummer = "dummy kommunenummer",
        tilleggsnavn = "dummy tilleggsnavn",
        postnummer = "dummy postnummer",
    )
}

fun createDummyPostadresseIFrittFormat(): PostadresseIFrittFormat {

    return PostadresseIFrittFormat(
        adresselinje1 = "dummy adresselinje1",
        adresselinje2 = "dummy adresselinje2",
        adresselinje3 = "dummy adresselinje3",
        postnummer = "dummy postnummer"
    )
}

fun createDummyPostboksadresse(): Postboksadresse {
    return Postboksadresse(
        postbokseier = "dummy postbokseier",
        postboks = "dummy postboks",
        postnummer = "dummy postnummer"
    )
}

fun createDummyUtenlandskAdresse(): UtenlandskAdresse {
    return UtenlandskAdresse(
        adressenavnNummer = "dummy adressenavnNummer",
        bygningEtasjeLeilighet = "dummy bygningEtasjeLeilighet",
        postboksNummerNavn = "dummy postboksNummerNavn",
        postkode = "dummy postkode",
        bySted = "dummy bySted",
        regionDistriktOmraade = "dummy regionDistriktOmraade",
        landkode = "dummy landkode"
    )
}

fun createDummyUtenlandskAdresseIFrittFormat(): UtenlandskAdresseIFrittFormat {
    return UtenlandskAdresseIFrittFormat(
        adresselinje1 = "dummy adresselinje1",
        adresselinje2 = "dummy adresselinje2",
        adresselinje3 = "dummy adresselinje3",
        postkode = "dummy postkode",
        byEllerStedsnavn = "dummy byEllerStedsnavn",
        landkode = "dummy landkode"
    )
}

fun createDummyMatrikkeladresse(): Matrikkeladresse {
    return Matrikkeladresse(
        bruksenhetsnummer = "dummy bruksenhetsnummer",
        tilleggsnavn = "dummy tilleggsnavn",
        postnummer = "dummy postnummer",
        kommunenummer = "dummy kommunenummer",
    )
}

fun createDummyUkjentbosted(): UkjentBosted {
    return UkjentBosted(
        bostedskommune = "dummy bostedskommune",
    )
}

fun createDummyMetadata() = no.nav.pdl.generated.dto.hentpersonquery.Metadata(
    master = "pdl",
)
