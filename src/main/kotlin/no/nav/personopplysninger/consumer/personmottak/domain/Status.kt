package no.nav.personopplysninger.consumer.personmottak.domain

class Status {
    val endringId: Int? = null
    val statusType: String? = null
    var substatus: List<Substatus> = emptyList()
}
