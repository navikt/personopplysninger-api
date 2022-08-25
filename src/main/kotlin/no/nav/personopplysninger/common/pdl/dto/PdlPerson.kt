package no.nav.personopplysninger.common.pdl.dto

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.common.pdl.dto.adresse.PdlBostedsadresse
import no.nav.personopplysninger.common.pdl.dto.adresse.PdlDeltBosted
import no.nav.personopplysninger.common.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.common.pdl.dto.adresse.PdlOppholdsadresse
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlFoedsel
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlFolkeregisteridentifikator
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlKjoenn
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlNavn
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlSivilstand
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlStatsborgerskap
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlTelefonnummer

@Serializable
data class PdlPerson(
    val navn: List<PdlNavn> = emptyList(),
    val telefonnummer: List<PdlTelefonnummer> = emptyList(),
    val folkeregisteridentifikator: List<PdlFolkeregisteridentifikator> = emptyList(),
    val statsborgerskap: List<PdlStatsborgerskap> = emptyList(),
    val foedsel: List<PdlFoedsel> = emptyList(),
    val sivilstand: List<PdlSivilstand> = emptyList(),
    val kjoenn: List<PdlKjoenn> = emptyList(),
    val bostedsadresse: List<PdlBostedsadresse> = emptyList(),
    val deltBosted: List<PdlDeltBosted> = emptyList(),
    val kontaktadresse: List<PdlKontaktadresse> = emptyList(),
    val oppholdsadresse: List<PdlOppholdsadresse> = emptyList(),
) {
    fun getKontaktadresseWithPdlMaster(): PdlKontaktadresse? {
        return kontaktadresse.firstOrNull { adresse -> adresse.metadata.master.equals("pdl", true) }
    }
}