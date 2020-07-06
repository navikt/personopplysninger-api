package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadressetype
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKoordinater
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlVegadresse
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