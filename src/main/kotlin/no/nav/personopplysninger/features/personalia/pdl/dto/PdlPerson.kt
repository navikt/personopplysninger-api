package no.nav.personopplysninger.features.personalia.pdl.dto

import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlBostedsadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlDeltBosted
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlOppholdsadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.personalia.*

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
)