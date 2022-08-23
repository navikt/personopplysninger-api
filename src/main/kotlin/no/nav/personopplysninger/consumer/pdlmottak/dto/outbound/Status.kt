package no.nav.personopplysninger.consumer.pdlmottak.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
class Status {
    val endringId: Int? = null
    val statusType: String? = null
    var substatus: List<Substatus> = emptyList()
}
