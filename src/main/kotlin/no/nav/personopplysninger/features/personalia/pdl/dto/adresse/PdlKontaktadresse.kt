package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

import no.nav.personopplysninger.features.personalia.pdl.dto.common.PdlMetadata
import java.time.LocalDateTime

data class PdlKontaktadresse (
        val gyldigFraOgMed: LocalDateTime?,
        val gyldigTilOgMed: LocalDateTime?,
        val type: PdlKontaktadressetype,
        val coAdressenavn: String?,
        val postboksadresse: PdlPostboksadresse?,
        val vegadresse: PdlVegadresse?,
        val postadresseIFrittFormat: PdlPostadresseIFrittFormat?,
        val utenlandskAdresse: PdlUtenlandskAdresse?,
        val utenlandskAdresseIFrittFormat: PdlUtenlandskAdresseIFrittFormat?,
        val folkeregistermetadata: PdlFolkeregistermetadata?,
        val metadata: PdlMetadata
)