package no.nav.personopplysninger.features.endreopplysninger.domain.telefon

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringTelefon : Endring<EndringTelefon>() {
    val innmeldtEndring: Telefonnummer? = null
}
