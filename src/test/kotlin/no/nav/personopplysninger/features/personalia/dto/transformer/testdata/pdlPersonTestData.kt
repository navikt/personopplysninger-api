package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.consumer.pdl.dto.PdlGeografiskTilknytning
import no.nav.personopplysninger.consumer.pdl.dto.PdlPerson
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlBostedsadresse
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlDeltBosted
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlKontaktadressetype
import no.nav.personopplysninger.consumer.pdl.dto.adresse.PdlOppholdsadresse
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlFoedsel
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlFolkeregisteridentifikator
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlKjoenn
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlKjoennType
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlNavn
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlSivilstand
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlSivilstandstype
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlStatsborgerskap
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlTelefonnummer
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.POSTADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
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
            PdlTelefonnummer("+47", "97505050", 1, createDummyMetadata()),
            PdlTelefonnummer("+47", "22113344", 2, createDummyMetadata())
        ),
        folkeregisteridentifikator = listOf(
            PdlFolkeregisteridentifikator(
                "identifikasjonsnummer", "status", "type"
            )
        ),
        statsborgerskap = listOf(
            PdlStatsborgerskap(
                land = "land",
                gyldigTilOgMed = null
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
        createDummyMetadata()
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
        createDummyMetadata()
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
        createDummyMetadata()
    )
}

fun createDummyGeografiskTilknytning(): PdlGeografiskTilknytning {
    return PdlGeografiskTilknytning("gtKommune", "gtBydel", "gtLand")
}