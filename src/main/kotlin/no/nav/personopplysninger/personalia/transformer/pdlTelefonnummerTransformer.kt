package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.pdl.dto.personalia.PdlTelefonnummer
import no.nav.personopplysninger.personalia.dto.outbound.Tlfnr

fun List<PdlTelefonnummer>.toTlfnr(): Tlfnr {
    val hoved = find { nummer -> nummer.prioritet == 1}
    val alternativ = find { nummer -> nummer.prioritet == 2}

    return Tlfnr(
            telefonHoved = hoved?.nummer,
            landskodeHoved = hoved?.landskode,
            telefonAlternativ =  alternativ?.nummer,
            landskodeAlternativ = alternativ?.landskode
    )
}