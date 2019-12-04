package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Error {
    var message: String? = null
    val details: Map<String, List<String>>? = null
}
