package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontonummer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Bank {
    val adresseLinje1: String? = null
    val adresseLinje2: String? = null
    val adresseLinje3: String? = null
    val kode: String? = null
    val navn: String? = null
}
