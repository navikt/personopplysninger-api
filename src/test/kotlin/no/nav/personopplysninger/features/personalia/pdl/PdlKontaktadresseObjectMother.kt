package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.*
import no.nav.personopplysninger.features.personalia.pdl.dto.common.PdlMetadata
import java.time.LocalDateTime

object PdlKontaktadresseObjectMother {
    fun dummyVegadresse(): PdlKontaktadresse {

        val vegadresse = PdlVegadresse(
                matrikkelId = 0,
                husnummer = "dummy husnummer",
                husbokstav = "dummy husbokstav",
                bruksenhetsnummer = "dummy bruksenhetsnummer",
                adressenavn = "dummy adressenavn",
                kommunenummer = "dummy kommunenummer",
                tilleggsnavn = "dummy tilleggsnavn",
                postnummer = "dummy postnummer",
                koordinater = dummyKoordinater()
        )

        return PdlKontaktadresse(
                gyldigFraOgMed = LocalDateTime.parse("2020-01-01T00:00:00"),
                gyldigTilOgMed = LocalDateTime.parse("2020-07-01T00:00:00"),
                type = PdlKontaktadressetype.Innland,
                coAdressenavn = "dummy coAdressenavn",
                vegadresse = vegadresse,
                postadresseIFrittFormat = null,
                postboksadresse = null,
                utenlandskAdresse = null,
                utenlandskAdresseIFrittFormat = null,
                folkeregistermetadata = null,
                metadata = dummyMetadata()
        )
    }

    fun dummyPostadresseIFrittFormat(): PdlKontaktadresse {

        val postadresseIFrittFormat = PdlPostadresseIFrittFormat(
                adresselinje1 = "dummy adresselinje1",
                adresselinje2 = "dummy adresselinje2",
                adresselinje3 = "dummy adresselinje3",
                postnummer = "dummy postnummer"
        )

        return PdlKontaktadresse(
                gyldigFraOgMed = LocalDateTime.parse("2020-01-01T00:00:00"),
                gyldigTilOgMed = LocalDateTime.parse("2020-07-01T00:00:00"),
                type = PdlKontaktadressetype.Innland,
                coAdressenavn = "dummy coAdressenavn",
                vegadresse = null,
                postadresseIFrittFormat = postadresseIFrittFormat,
                postboksadresse = null,
                utenlandskAdresse = null,
                utenlandskAdresseIFrittFormat = null,
                folkeregistermetadata = null,
                metadata = dummyMetadata()
        )
    }

    fun dummyPostboksadresse(): PdlKontaktadresse {
        val postboksadresse = PdlPostboksadresse(
                postbokseier = "dummy postbokseier",
                postboks = "dummy postboks",
                postnummer = "dummy postnummer"
        )

        return PdlKontaktadresse(
                gyldigFraOgMed = LocalDateTime.parse("2020-01-01T00:00:00"),
                gyldigTilOgMed = LocalDateTime.parse("2020-07-01T00:00:00"),
                type = PdlKontaktadressetype.Innland,
                coAdressenavn = "dummy coAdressenavn",
                vegadresse = null,
                postadresseIFrittFormat = null,
                postboksadresse = postboksadresse,
                utenlandskAdresse = null,
                utenlandskAdresseIFrittFormat = null,
                folkeregistermetadata = null,
                metadata = dummyMetadata()
        )
    }

    fun dummyUtenlandskAdresse(): PdlKontaktadresse {
        val utenlandskAdresse = PdlUtenlandskAdresse(
                adressenavnNummer = "dummy adressenavnNummer",
                bygningEtasjeLeilighet = "dummy bygningEtasjeLeilighet",
                postboksNummerNavn = "dummy postboksNummerNavn",
                postkode = "dummy postkode",
                bySted = "dummy bySted",
                regionDistriktOmraade = "dummy regionDistriktOmraade",
                landkode = "dummy landkode"
        )

        return PdlKontaktadresse(
                gyldigFraOgMed = LocalDateTime.parse("2020-01-01T00:00:00"),
                gyldigTilOgMed = LocalDateTime.parse("2020-07-01T00:00:00"),
                type = PdlKontaktadressetype.Utland,
                coAdressenavn = "dummy coAdressenavn",
                vegadresse = null,
                postadresseIFrittFormat = null,
                postboksadresse = null,
                utenlandskAdresse = utenlandskAdresse,
                utenlandskAdresseIFrittFormat = null,
                folkeregistermetadata = null,
                metadata = dummyMetadata()
        )
    }

    fun dummyUtenlandskAdresseIFrittFormat(): PdlKontaktadresse {
        val utenlandskAdresseIFrittFormat = PdlUtenlandskAdresseIFrittFormat(
                adresselinje1 = "dummy adresselinje1",
                adresselinje2 = "dummy adresselinje2",
                adresselinje3 = "dummy adresselinje3",
                postkode = "dummy postkode",
                byEllerStedsnavn = "dummy byEllerStedsnavn",
                landkode = "dummy landkode"
        )

        return PdlKontaktadresse(
                gyldigFraOgMed = LocalDateTime.parse("2020-01-01T00:00:00"),
                gyldigTilOgMed = LocalDateTime.parse("2020-07-01T00:00:00"),
                type = PdlKontaktadressetype.Utland,
                coAdressenavn = "dummy coAdressenavn",
                vegadresse = null,
                postadresseIFrittFormat = null,
                postboksadresse = null,
                utenlandskAdresse = null,
                utenlandskAdresseIFrittFormat = utenlandskAdresseIFrittFormat,
                folkeregistermetadata = null,
                metadata = dummyMetadata()
        )
    }

    private fun dummyMetadata() = PdlMetadata (
            opplysningsId = "",
            master = "pdl",
            endringer = emptyList(),
            historisk = false
    )

    private fun dummyKoordinater() = PdlKoordinater (
            x = 0.0,
            y = 0.0,
            z = 0.0,
            kvalitet = 0
    )
}