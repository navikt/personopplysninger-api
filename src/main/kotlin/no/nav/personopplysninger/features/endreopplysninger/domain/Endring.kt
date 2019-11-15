package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Endring<T> {
    val endringstype: String? = null
    val ident: String? = null
    val lineage: String? = null
    val opplysningsId: String? = null
    val opplysningstype: String? = null
    var status: Status = Status()
    var statusType = "OK"
    var error: Error? = null

    val isPending: Boolean
        @JsonIgnore
        get() = "PENDING" == status.statusType

    val isDoneWithoutTpsError: Boolean
        @JsonIgnore
        get() = if ("DONE" != status.statusType) {
            false
        } else !hasTpsError()

    private val tpsBeskrivelse: String?
        @JsonIgnore
        get() {
            for (substatus in status.substatus) {
                if ("TPS".equals(substatus.domene, ignoreCase = true)) {
                    return substatus.beskrivelse
                }
            }
            return null
        }

    fun createValidationErrorIfTpsHasError() {
        if (hasTpsError()) {
            val validationError = Error()
            validationError.message = tpsBeskrivelse
            statusType = "ERROR"
            error = validationError
        }
    }

    @JsonIgnore
    private fun hasTpsError(): Boolean {
        for (substatus in status.substatus) {
            if ("TPS".equals(substatus.domene, ignoreCase = true)) {
                return "ERROR" == substatus.status
            }
        }
        return false
    }
}
