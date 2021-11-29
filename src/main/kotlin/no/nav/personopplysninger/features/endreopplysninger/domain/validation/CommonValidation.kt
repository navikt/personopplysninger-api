package no.nav.personopplysninger.features.endreopplysninger.domain.validation

import java.time.LocalDate
import java.time.format.DateTimeParseException

object CommonValidation {
    fun isNullOrBlank (string: String?): Boolean {
        return string == null || string.isBlank()
    }

    fun isValidDate(dateString: String?): Boolean {
        return try {
            LocalDate.parse(dateString)
            true
        } catch (dtpe: DateTimeParseException) {
            false
        } catch (npe: NullPointerException) {
            false
        }
    }
}