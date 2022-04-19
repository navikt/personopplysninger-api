package no.nav.personopplysninger.consumer.pdlmottak.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

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

    constructor()

    protected constructor(error: Error): this() {
        this.error = error
        this.statusType = "ERROR"
    }

    val confirmedOk: Boolean
        @JsonIgnore
        get() = ("DONE" == status.statusType && !hasTpsError()) || "OK" == status.statusType

    val errorMessage: String
        @JsonIgnore
        get() =
            if (hasTpsError()) "TPS rapporterer feil på oppdatering."
            else if (!isDone) "Endring har status $statusType"
            else ""

    val isPending: Boolean
        @JsonIgnore
        get() = "PENDING" == status.statusType

    private val isDone: Boolean
        @JsonIgnore
        get() = "DONE" == status.statusType

    val hasTpsError: Boolean
        @JsonIgnore
        get() = hasTpsError()

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
