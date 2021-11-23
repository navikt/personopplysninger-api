package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.features.personalia.pdl.dto.personalia.PdlTelefonnummer

val pdlPersonWithValues = PdlPerson(
        telefonnummer = listOf(
                PdlTelefonnummer("+47", "97505050", 1, "opl-123"),
                PdlTelefonnummer("+47", "22113344", 2, "opl-456")
        )
)