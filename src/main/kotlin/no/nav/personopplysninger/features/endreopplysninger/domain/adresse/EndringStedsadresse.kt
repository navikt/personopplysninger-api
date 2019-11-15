package no.nav.personopplysninger.features.endreopplysninger.domain.adresse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring

@JsonIgnoreProperties(ignoreUnknown = true)
class EndringStedsadresse : Endring<EndringStedsadresse>() {
    val innmeldtEndring: Stedsadresse? = null
}
