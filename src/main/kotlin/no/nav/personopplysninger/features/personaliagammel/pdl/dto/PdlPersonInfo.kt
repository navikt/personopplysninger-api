package no.nav.personopplysninger.features.personaliagammel.pdl.dto

import no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.telefon.PdlTelefonnummer

data class PdlPersonInfo (
        val telefonnummer: List<PdlTelefonnummer> = emptyList(),
        val kontaktadresse: List<PdlKontaktadresse> = emptyList()
)