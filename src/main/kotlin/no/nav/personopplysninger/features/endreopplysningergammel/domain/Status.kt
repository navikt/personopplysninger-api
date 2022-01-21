package no.nav.personopplysninger.features.endreopplysningergammel.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Status {
    val endringId: Int? = null
    val statusType: String? = null
    var substatus: List<Substatus> = emptyList()
}
