package no.nav.personopplysninger.features.endreopplysninger.domain.validation

import no.nav.personopplysninger.features.endreopplysninger.domain.Error

class ValidationResult<T, R>  private constructor (val result: T?, val error: Error?) {

    private var responseObj: R? = null

    val response get() = responseObj!!

    fun ifValid(block: (T) -> R): ValidationResult<T, R> {
        if (result != null) {
            responseObj = block.invoke(result)
        }
        return this
    }

    fun ifInvalid(block: (Error) -> R): ValidationResult<T, R> {
        if (error != null) {
            responseObj = block.invoke(error)
        }
        return this
    }

    companion object {
        fun <T, R> validResult(result: T) = ValidationResult<T, R>(result, null)

        fun <T, R> invalidResult(error: Error) = ValidationResult<T, R>(null, error)
    }

}
