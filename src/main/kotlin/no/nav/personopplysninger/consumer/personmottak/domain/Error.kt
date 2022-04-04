package no.nav.personopplysninger.consumer.personmottak.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Error {
    var message: String? = null
    val details: Map<String, List<String>>? = null
}
