package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.consumer.pdl.dto.adresse.*
import no.nav.personopplysninger.consumer.pdl.dto.common.PdlMetadata

fun createDummyVegadresse(): PdlVegadresse {

    return PdlVegadresse(
        matrikkelId = 0,
        husnummer = "dummy husnummer",
        husbokstav = "dummy husbokstav",
        bruksenhetsnummer = "dummy bruksenhetsnummer",
        adressenavn = "dummy adressenavn",
        kommunenummer = "dummy kommunenummer",
        bydelsnummer = "dummy bydelsnummer",
        tilleggsnavn = "dummy tilleggsnavn",
        postnummer = "dummy postnummer",
        koordinater = createDummyKoordinater()
    )
}

fun createDummyPostadresseIFrittFormat(): PdlPostadresseIFrittFormat {

    return PdlPostadresseIFrittFormat(
        adresselinje1 = "dummy adresselinje1",
        adresselinje2 = "dummy adresselinje2",
        adresselinje3 = "dummy adresselinje3",
        postnummer = "dummy postnummer"
    )
}

fun createDummyPostboksadresse(): PdlPostboksadresse {
    return PdlPostboksadresse(
        postbokseier = "dummy postbokseier",
        postboks = "dummy postboks",
        postnummer = "dummy postnummer"
    )
}

fun createDummyUtenlandskAdresse(): PdlUtenlandskAdresse {
    return PdlUtenlandskAdresse(
        adressenavnNummer = "dummy adressenavnNummer",
        bygningEtasjeLeilighet = "dummy bygningEtasjeLeilighet",
        postboksNummerNavn = "dummy postboksNummerNavn",
        postkode = "dummy postkode",
        bySted = "dummy bySted",
        regionDistriktOmraade = "dummy regionDistriktOmraade",
        landkode = "dummy landkode"
    )
}

fun createDummyUtenlandskAdresseIFrittFormat(): PdlUtenlandskAdresseIFrittFormat {
    return PdlUtenlandskAdresseIFrittFormat(
        adresselinje1 = "dummy adresselinje1",
        adresselinje2 = "dummy adresselinje2",
        adresselinje3 = "dummy adresselinje3",
        postkode = "dummy postkode",
        byEllerStedsnavn = "dummy byEllerStedsnavn",
        landkode = "dummy landkode"
    )
}

fun createDummyMatrikkeladresse(): PdlMatrikkeladresse {
    return PdlMatrikkeladresse(
        bruksenhetsnummer = "dummy bruksenhetsnummer",
        tilleggsnavn = "dummy tilleggsnavn",
        postnummer = "dummy postnummer",
        kommunenummer = "dummy kommunenummer",
    )
}

fun createDummyUkjentbosted(): PdlUkjentbosted {
    return PdlUkjentbosted(
        bostedskommune = "dummy bostedskommune",
    )
}

fun createDummyMetadata() = PdlMetadata(
    opplysningsId = "",
    master = "pdl",
    endringer = emptyList(),
    historisk = false
)

private fun createDummyKoordinater() = PdlKoordinater(
    x = 0.0,
    y = 0.0,
    z = 0.0,
    kvalitet = 0
)