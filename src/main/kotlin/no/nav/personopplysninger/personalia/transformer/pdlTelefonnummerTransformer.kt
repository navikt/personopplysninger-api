package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.Telefonnummer
import no.nav.personopplysninger.personalia.dto.outbound.Tlfnr

fun List<Telefonnummer>.toTlfnr(): Tlfnr {
    val hoved = find { it.prioritet == 1}
    val alternativ = find { it.prioritet == 2}

    return Tlfnr(
            telefonHoved = hoved?.nummer,
            landskodeHoved = hoved?.landskode,
            telefonAlternativ =  alternativ?.nummer,
            landskodeAlternativ = alternativ?.landskode
    )
}