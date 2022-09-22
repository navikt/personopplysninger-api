package no.nav.personopplysninger.endreopplysninger.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class Endring(
    var status: Status = Status(),
    var statusType: String = "OK",
    var error: Error? = null
) {
    fun confirmedOk(): Boolean {
        return ("DONE" == status.statusType && !hasTpsError()) || "OK" == status.statusType
    }

    fun isPending(): Boolean {
        return "PENDING" == status.statusType
    }

    private fun getTpsBeskrivelse(): String? {
        for (substatus in status.substatus) {
            if ("TPS".equals(substatus.domene, ignoreCase = true)) {
                return substatus.beskrivelse
            }
        }
        return null
    }

    fun createValidationErrorIfTpsHasError() {
        if (hasTpsError()) {
            val validationError = Error(message = getTpsBeskrivelse())
            this.statusType = "ERROR"
            this.error = validationError
        }
    }

    private fun hasTpsError(): Boolean {
        for (substatus in status.substatus) {
            if ("TPS".equals(substatus.domene, ignoreCase = true)) {
                return "ERROR" == substatus.status
            }
        }
        return false
    }
}