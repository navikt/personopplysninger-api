package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.Error
import no.nav.personopplysninger.features.endreopplysninger.domain.validation.CommonValidation.isNullOrBlank
import no.nav.personopplysninger.features.endreopplysninger.domain.validation.CommonValidation.isValidDate
import no.nav.personopplysninger.features.endreopplysninger.domain.validation.ValidationResult
import no.nav.personopplysninger.features.endreopplysninger.domain.validation.ValidationResult.Companion.invalidResult
import no.nav.personopplysninger.features.endreopplysninger.domain.validation.ValidationResult.Companion.validResult
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.*
import java.time.LocalDate

fun validateVegadresse(vegadresse: DownstreamVegadresse): ValidationResult<DownstreamVegadresse, EndringKontaktadresse> {

    if (isNullOrBlank(vegadresse.adressenavn)) {
        val error = Error().apply {
            message = "Adressenavn kan ikke være blankt."
        }
        return invalidResult(error)
    }

    if (isNullOrBlank(vegadresse.postnummer)) {
        val error = Error().apply {
            message = "Postnummer kan ikke være blankt."
        }
        return invalidResult(error)
    }

    if (!isValidDate(vegadresse.gyldigFraOgMed)) {
        val error = Error().apply {
            message = "GyldigFraOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (!isValidDate(vegadresse.gyldigTilOgMed)) {
        val error = Error().apply {
            message = "GyldigTilOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (dateIsMoreThanOneYearInTheFuture(vegadresse.gyldigTilOgMed!!)) {
        val error = Error().apply {
            message = "GyldigTilOgMed kan ikke være mer enn ett år frem i tid."
        }
        return invalidResult(error)
    }

    return validResult(vegadresse)
}

fun validatePostboksAdresse(postboksadresse: DownstreamPostboksadresse): ValidationResult<DownstreamPostboksadresse, EndringKontaktadresse> {

    if (isNullOrBlank(postboksadresse.postboks)) {
        val error = Error().apply {
            message = "Postboks kan ikke være blank."
        }
        return invalidResult(error)
    }

    if (isNullOrBlank(postboksadresse.postnummer)) {
        val error = Error().apply {
            message = "Postboks kan ikke være blankt."
        }
        return invalidResult(error)
    }

    if (!isValidDate(postboksadresse.gyldigFraOgMed)) {
        val error = Error().apply {
            message = "GyldigFraOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (!isValidDate(postboksadresse.gyldigTilOgMed)) {
        val error = Error().apply {
            message = "GyldigTilOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (dateIsMoreThanOneYearInTheFuture(postboksadresse.gyldigTilOgMed!!)) {
        val error = Error().apply {
            message = "GyldigTilOgMed kan ikke være mer enn ett år frem i tid."
        }
        return invalidResult(error)
    }

    return validResult(postboksadresse)
}

fun validateUtenlandskAdresse(utenlandskAdresse: DownstreamUtenlandskAdresse): ValidationResult<DownstreamUtenlandskAdresse, EndringKontaktadresse> {

    if (isNullOrBlank(utenlandskAdresse.landkode)) {
        val error = Error().apply {
            message = "Postboks kan ikke være blank."
        }
        return invalidResult(error)
    }

    if (!isValidDate(utenlandskAdresse.gyldigFraOgMed)) {
        val error = Error().apply {
            message = "GyldigFraOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (!isValidDate(utenlandskAdresse.gyldigTilOgMed)) {
        val error = Error().apply {
            message = "GyldigTilOgMed er ikke en gyldig dato."
        }
        return invalidResult(error)
    }

    if (dateIsMoreThanOneYearInTheFuture(utenlandskAdresse.gyldigTilOgMed!!)) {
        val error = Error().apply {
            message = "GyldigTilOgMed kan ikke være mer enn ett år frem i tid."
        }
        return invalidResult(error)
    }

    return validResult(utenlandskAdresse)
}

private fun dateIsMoreThanOneYearInTheFuture(dateString: String): Boolean {
    val date = LocalDate.parse(dateString)

    return date.isAfter(LocalDate.now().plusYears(1))
}