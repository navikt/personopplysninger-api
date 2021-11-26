package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlGeografiskTilknytning
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.*
import no.nav.personopplysninger.features.personalia.pdl.dto.personalia.*
import java.time.LocalDate
import java.time.LocalDateTime


fun createDummyPdlData(): PdlData {
    return PdlData(createDummyPerson(), createDummyGeografiskTilknytning())
}

fun createDummyPdlDataWithoutAdresser(): PdlData {
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

fun createDummyPerson(): PdlPerson {
    return PdlPerson(
        navn = listOf(
            PdlNavn("fornavn", "mellomnavn", "etternavn")
        ),
        telefonnummer = listOf(
            PdlTelefonnummer("+47", "97505050", 1, "opl-123"),
            PdlTelefonnummer("+47", "22113344", 2, "opl-456")
        ),
        folkeregisteridentifikator = listOf(
            PdlFolkeregisteridentifikator(
                "identifikasjonsnummer", "status", "type"
            )
        ),
        statsborgerskap = listOf(
            PdlStatsborgerskap(
                "land"
            )
        ),
        foedsel = listOf(
            PdlFoedsel("foedested", "foedekommune", "foedeland")
        ),
        sivilstand = listOf(
            PdlSivilstand(PdlSivilstandstype.GIFT, LocalDate.now().minusDays(1000))
        ),
        kjoenn = listOf(
            PdlKjoenn(PdlKjoennType.KVINNE)
        ),
        bostedsadresse = listOf(createDummyBostedsadresse(MATRIKKELADRESSE)),
        deltBosted = listOf(createDummyDeltBosted(VEGADRESSE)),
        kontaktadresse = listOf(createDummyKontaktadresse(VEGADRESSE)),
        oppholdsadresse = listOf(createDummyOppholdsadresse(UTENLANDSK_ADRESSE))
    )
}

fun createDummyBostedsadresse(adresseType: AdresseType): PdlBostedsadresse {
    return PdlBostedsadresse(
        LocalDate.now().minusDays(1000),
        LocalDateTime.now().minusDays(1000),
        LocalDateTime.now().plusDays(1000),
        "coAdressenavn",
        if(adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if(adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        if(adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if(adresseType == UKJENTBOSTED) createDummyUkjentbosted() else null,
    )
}

fun createDummyDeltBosted(adresseType: AdresseType): PdlDeltBosted {
    return PdlDeltBosted(
        LocalDate.now().minusDays(1000),
        LocalDate.now().plusDays(1000),
        "coAdressenavn",
        if(adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if(adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        if(adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if(adresseType == UKJENTBOSTED) createDummyUkjentbosted() else null,
    )
}

fun createDummyKontaktadresse(adresseType: AdresseType): PdlKontaktadresse {
    return PdlKontaktadresse(
        LocalDateTime.now().minusDays(1000),
        LocalDateTime.now().plusDays(1000),
        PdlKontaktadressetype.Innland,
        "coAdressenavn",
        if(adresseType == POSTBOKSADRESSE) createDummyPostboksadresse() else null,
        if(adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if(adresseType == POSTADRESSE_I_FRITT_FORMAT) createDummyPostadresseIFrittFormat() else null,
        if(adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if(adresseType == UTENLANDSK_ADRESSE_I_FRITT_FORMAT) createDummyUtenlandskAdresseIFrittFormat() else null,
        null,
        createDummyMetadata()
    )
}

fun createDummyOppholdsadresse(adresseType: AdresseType): PdlOppholdsadresse {
    return PdlOppholdsadresse(
        LocalDateTime.now().minusDays(1000),
        LocalDateTime.now().plusDays(1000),
        "coAdressenavn",
        if(adresseType == UTENLANDSK_ADRESSE) createDummyUtenlandskAdresse() else null,
        if(adresseType == VEGADRESSE) createDummyVegadresse() else null,
        if(adresseType == MATRIKKELADRESSE) createDummyMatrikkeladresse() else null,
        null,
    )
}

fun createDummyGeografiskTilknytning(): PdlGeografiskTilknytning {
    return PdlGeografiskTilknytning("gtKommune", "gtBydel", "gtLand")
}