package no.nav.personopplysninger.endreopplysninger.extensions

import no.nav.pdl.generated.dto.HentTelefonQuery

fun HentTelefonQuery.Result.findOpplysningsId(landskode: String, telefonnummer: String): String? {
    return person?.telefonnummer
        ?.find { it.landskode == landskode && it.nummer == telefonnummer }
        ?.metadata
        ?.opplysningsId
}