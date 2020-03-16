package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlTelefonnummer

val pdlPersonInfoWithValues = PdlPersonInfo(
        telefonnummer = listOf(
                PdlTelefonnummer("+47", "97505050", 1, "opl-123"),
                PdlTelefonnummer("+47", "22113344", 2, "opl-456")
        )
)