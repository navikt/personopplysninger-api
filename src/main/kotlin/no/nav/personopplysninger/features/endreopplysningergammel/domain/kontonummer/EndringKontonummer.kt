package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontonummer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Endring

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringKontonummer : Endring<EndringKontonummer>() {
    val innmeldtEndring: Kontonummer? = null
}
