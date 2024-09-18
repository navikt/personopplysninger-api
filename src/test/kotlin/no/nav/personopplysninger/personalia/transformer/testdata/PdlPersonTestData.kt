package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.enums.KjoennType
import no.nav.pdl.generated.dto.enums.Sivilstandstype
import no.nav.pdl.generated.dto.hentpersonquery.Bostedsadresse
import no.nav.pdl.generated.dto.hentpersonquery.DeltBosted
import no.nav.pdl.generated.dto.hentpersonquery.Foedested
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


val defaultPerson = Person(
    navn = listOf(
        Navn("fornavn", "mellomnavn", "etternavn")
    ),
    telefonnummer = listOf(
        Telefonnummer("+47", "97505050", 1),
        Telefonnummer("+47", "22113344", 2)
    ),
    folkeregisteridentifikator = listOf(
        Folkeregisteridentifikator(
            "identifikasjonsnummer", "type"
        )
    ),
    statsborgerskap = listOf(
        Statsborgerskap(
            land = "land",
            gyldigTilOgMed = null
        )
    ),
    foedested = listOf(
        Foedested("foedekommune", "foedeland")
    ),
    sivilstand = listOf(
        Sivilstand(Sivilstandstype.GIFT)
    ),
    kjoenn = listOf(
        Kjoenn(KjoennType.KVINNE)
    ),
    bostedsadresse = listOf(createBostedsadresse(MATRIKKELADRESSE)),
    deltBosted = listOf(createDeltBosted(VEGADRESSE)),
    kontaktadresse = listOf(createKontaktadresse(VEGADRESSE)),
    oppholdsadresse = listOf(createOppholdsadresse(UTENLANDSK_ADRESSE))
)

val defaultPdlData = HentPersonQuery.Result(defaultPerson, createGeografiskTilknytning())

val defaultPdlDataWithoutAdresser = defaultPdlData.copy(
    person = defaultPerson.copy(
        oppholdsadresse = emptyList(),
        deltBosted = emptyList(),
        kontaktadresse = emptyList(),
        bostedsadresse = emptyList(),
    ),
    geografiskTilknytning = null
)

fun createBostedsadresse(adresseType: AdresseType): Bostedsadresse {
    return Bostedsadresse(
        angittFlyttedato = LocalDate.now().minusDays(1000).toString(),
        coAdressenavn = "coAdressenavn",
        vegadresse = if (adresseType == VEGADRESSE) defaultVegadresse else null,
        matrikkeladresse = if (adresseType == MATRIKKELADRESSE) defaultMatrikkeladresse else null,
        utenlandskAdresse = if (adresseType == UTENLANDSK_ADRESSE) defaultUtenlandskAdresse else null,
        ukjentBosted = if (adresseType == UKJENTBOSTED) defaultUkjentbosted else null,
    )
}

fun createDeltBosted(adresseType: AdresseType): DeltBosted {
    return DeltBosted(
        coAdressenavn = "coAdressenavn",
        vegadresse = if (adresseType == VEGADRESSE) defaultVegadresse else null,
        matrikkeladresse = if (adresseType == MATRIKKELADRESSE) defaultMatrikkeladresse else null,
        utenlandskAdresse = if (adresseType == UTENLANDSK_ADRESSE) defaultUtenlandskAdresse else null,
        ukjentBosted = if (adresseType == UKJENTBOSTED) defaultUkjentbosted else null,
    )
}

fun createKontaktadresse(adresseType: AdresseType): Kontaktadresse {
    return Kontaktadresse(
        gyldigTilOgMed = LocalDateTime.now().plusDays(1000).toString(),
        coAdressenavn = "coAdressenavn",
        postboksadresse = if (adresseType == POSTBOKSADRESSE) defaultPostboksadresse else null,
        vegadresse = if (adresseType == VEGADRESSE) defaultVegadresse else null,
        postadresseIFrittFormat = if (adresseType == POSTADRESSE_I_FRITT_FORMAT) defaultPostadresseIFrittFormat else null,
        utenlandskAdresse = if (adresseType == UTENLANDSK_ADRESSE) defaultUtenlandskAdresse else null,
        utenlandskAdresseIFrittFormat = if (adresseType == UTENLANDSK_ADRESSE_I_FRITT_FORMAT) defaultUtenlandskAdresseIFrittFormat else null,
        metadata = defaultMetadata
    )
}

fun createOppholdsadresse(adresseType: AdresseType): Oppholdsadresse {
    return Oppholdsadresse(
        gyldigTilOgMed = LocalDateTime.now().plusDays(1000).toString(),
        coAdressenavn = "coAdressenavn",
        utenlandskAdresse = if (adresseType == UTENLANDSK_ADRESSE) defaultUtenlandskAdresse else null,
        vegadresse = if (adresseType == VEGADRESSE) defaultVegadresse else null,
        matrikkeladresse = if (adresseType == MATRIKKELADRESSE) defaultMatrikkeladresse else null,
        oppholdAnnetSted = null,
        metadata = defaultMetadata
    )
}

fun createGeografiskTilknytning(): GeografiskTilknytning {
    return GeografiskTilknytning("gtKommune", "gtBydel", "gtLand")
}