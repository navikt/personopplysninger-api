package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.enums.KjoennType

val KjoennType.stringValue: String
    get() = when (this) {
        KjoennType.MANN -> "Mann"
        KjoennType.KVINNE -> "Kvinne"
        else -> "Ukjent"
    }