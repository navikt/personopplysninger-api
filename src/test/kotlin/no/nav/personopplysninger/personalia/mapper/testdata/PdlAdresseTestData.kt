package no.nav.personopplysninger.personalia.mapper.testdata

import no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse
import no.nav.pdl.generated.dto.hentpersonquery.Metadata
import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse
import no.nav.pdl.generated.dto.hentpersonquery.UkjentBosted
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Vegadresse

val defaultVegadresse = Vegadresse(
    husnummer = "dummy husnummer",
    husbokstav = "dummy husbokstav",
    bruksenhetsnummer = "dummy bruksenhetsnummer",
    adressenavn = "dummy adressenavn",
    kommunenummer = "dummy kommunenummer",
    tilleggsnavn = "dummy tilleggsnavn",
    postnummer = "dummy postnummer",
)

val defaultPostadresseIFrittFormat = PostadresseIFrittFormat(
    adresselinje1 = "dummy adresselinje1",
    adresselinje2 = "dummy adresselinje2",
    adresselinje3 = "dummy adresselinje3",
    postnummer = "dummy postnummer"
)

val defaultPostboksadresse = Postboksadresse(
    postbokseier = "dummy postbokseier",
    postboks = "dummy postboks",
    postnummer = "dummy postnummer"
)

val defaultUtenlandskAdresse = UtenlandskAdresse(
    adressenavnNummer = "dummy adressenavnNummer",
    bygningEtasjeLeilighet = "dummy bygningEtasjeLeilighet",
    postboksNummerNavn = "dummy postboksNummerNavn",
    postkode = "dummy postkode",
    bySted = "dummy bySted",
    regionDistriktOmraade = "dummy regionDistriktOmraade",
    landkode = "dummy landkode"
)

val defaultUtenlandskAdresseIFrittFormat = UtenlandskAdresseIFrittFormat(
    adresselinje1 = "dummy adresselinje1",
    adresselinje2 = "dummy adresselinje2",
    adresselinje3 = "dummy adresselinje3",
    landkode = "dummy landkode"
)

val defaultMatrikkeladresse = Matrikkeladresse(
    bruksenhetsnummer = "dummy bruksenhetsnummer",
    tilleggsnavn = "dummy tilleggsnavn",
    postnummer = "dummy postnummer",
    kommunenummer = "dummy kommunenummer",
)

val defaultUkjentbosted = UkjentBosted(
    bostedskommune = "dummy bostedskommune",
)

val defaultMetadata = Metadata(
    master = "pdl",
)
