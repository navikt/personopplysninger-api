package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.enums.KjoennType
import no.nav.pdl.generated.dto.enums.KontaktadresseType
import no.nav.pdl.generated.dto.enums.Sivilstandstype
import no.nav.pdl.generated.dto.hentpersonquery.Bostedsadresse
import no.nav.pdl.generated.dto.hentpersonquery.DeltBosted
import no.nav.pdl.generated.dto.hentpersonquery.Foedsel
import no.nav.pdl.generated.dto.hentpersonquery.Folkeregisteridentifikator
import no.nav.pdl.generated.dto.hentpersonquery.GeografiskTilknytning
import no.nav.pdl.generated.dto.hentpersonquery.Kjoenn
import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse
import no.nav.pdl.generated.dto.hentpersonquery.Navn
import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.pdl.generated.dto.hentpersonquery.Sivilstand
import no.nav.pdl.generated.dto.hentpersonquery.Statsborgerskap
import no.nav.pdl.generated.dto.hentpersonquery.Telefonnummer
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import java.time.LocalDate
import java.time.LocalDateTime


fun createDummyPdlData(): HentPersonQuery.Result {
    return HentPersonQuery.Result(createDummyPerson(), createDummyGeografiskTilknytning())
}

fun createDummyPdlDataWithoutAdresser(): HentPersonQuery.Result {
    return createDummyPdlData().copy(
        person = createDummyPerson().copy(
            oppholdsadresse = emptyList(),
            deltBosted = emptyList(),
            kontaktadresse = emptyList(),
            bostedsadresse = emptyList(),
        ),
        geografiskTilknytning = null
    )
}

fun createDummyPerson(): Person {
    return Person(
        navn = listOf(
            Navn("fornavn", "mellomnavn", "etternavn")
        ),
        telefonnummer = listOf(
            Telefonnummer("+47", "97505050", 1),
            Telefonnummer("+47", "22113344", 2)
        ),
        folkeregisteridentifikator = listOf(
            Folkeregisteridentifikator(
                "identifikasjonsnummer", "status", "type"
            )
        ),
        statsborgerskap = listOf(
            Statsborgerskap(
                land = "land",
                gyldigTilOgMed = null
            )
        ),
        foedsel = listOf(
            Foedsel("foedested", "foedekommune", "foedeland")
        ),
        sivilstand = listOf(
            Sivilstand(Sivilstandstype.GIFT, LocalDate.now().minusDays(1000).toString())
        ),
        kjoenn = listOf(
            Kjoenn(KjoennType.KVINNE)
        ),
        bostedsadresse = listOf(createDummyBostedsadresse(MATRIKKELADRESSE)),
        deltBosted = listOf(createDummyDeltBosted(VEGADRESSE)),
        kontaktadresse = listOf(createDummyKontaktadresse(VEGADRESSE)),
        oppholdsadresse = listOf(createDummyOppholdsadresse(UTENLANDSK_ADRESSE))
    )
}

fun createDummyBostedsadresse(adresseType: AdresseType): Bostedsadresse {
    return Bostedsadresse(
        LocalDate.now().minusDays(1000).toString(),
        LocalDateTime.now().minusDays(1000).toString(),
        LocalDateTime.now().plusDays(1000).toString(),
        "coAdressenavn",
        if (adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if (adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        if (adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if (adresseType == UKJENTBOSTED) createDummyUkjentbosted() else null,
        createDummyMetadata()
    )
}

fun createDummyDeltBosted(adresseType: AdresseType): DeltBosted {
    return DeltBosted(
        LocalDate.now().minusDays(1000).toString(),
        LocalDate.now().plusDays(1000).toString(),
        "coAdressenavn",
        if (adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if (adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        if (adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if (adresseType == UKJENTBOSTED) createDummyUkjentbosted() else null,
        createDummyMetadata()
    )
}

fun createDummyKontaktadresse(adresseType: AdresseType): Kontaktadresse {
    return Kontaktadresse(
        LocalDateTime.now().minusDays(1000).toString(),
        LocalDateTime.now().plusDays(1000).toString(),
        KontaktadresseType.INNLAND,
        "coAdressenavn",
        if (adresseType == POSTBOKSADRESSE) createDummyPostboksadresse() else null,
        if (adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if (adresseType == POSTADRESSE_I_FRITT_FORMAT) createDummyPostadresseIFrittFormat() else null,
        if (adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if (adresseType == UTENLANDSK_ADRESSE_I_FRITT_FORMAT) createDummyUtenlandskAdresseIFrittFormat() else null,
        null,
        createDummyMetadata()
    )
}

fun createDummyOppholdsadresse(adresseType: AdresseType): Oppholdsadresse {
    return Oppholdsadresse(
        LocalDateTime.now().minusDays(1000).toString(),
        LocalDateTime.now().plusDays(1000).toString(),
        "coAdressenavn",
        if (adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if (adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if (adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        null,
        createDummyMetadata()
    )
}

fun createDummyGeografiskTilknytning(): GeografiskTilknytning? {
    return GeografiskTilknytning("gtKommune", "gtBydel", "gtLand")
}