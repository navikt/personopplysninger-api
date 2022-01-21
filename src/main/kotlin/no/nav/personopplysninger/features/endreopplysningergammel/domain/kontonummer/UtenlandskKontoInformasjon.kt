package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontonummer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UtenlandskKontoInformasjon {
    val bank: Bank? = null
    val landkode: String? = null
    val swift: String? = null
    val valuta: String? = null
}
