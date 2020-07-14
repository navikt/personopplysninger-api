package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.features.personalia.pdl.dto.telefon.PdlTelefonnummer

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