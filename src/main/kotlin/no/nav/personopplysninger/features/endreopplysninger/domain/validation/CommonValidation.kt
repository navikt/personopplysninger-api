package no.nav.personopplysninger.features.endreopplysninger.domain.validation

import java.lang.NullPointerException
import java.time.LocalDate
import java.time.LocalDateTime
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

    fun isValidDateTime(dateTimeString: String?): Boolean {
        return try {
            LocalDateTime.parse(dateTimeString)
            true
        } catch (dtpe: DateTimeParseException) {
            false
        } catch (npe: NullPointerException) {
            false
        }
    }
}