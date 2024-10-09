package no.nav.personopplysninger.endreopplysninger.extensions

import no.nav.pdl.generated.dto.HentKontaktadresseQuery

private const val PDL_MASTER = "pdl"

fun HentKontaktadresseQuery.Result.findOpplysningsId(): String? {
    return person?.kontaktadresse
        ?.firstOrNull { it.metadata.master.equals(PDL_MASTER, true) }
        ?.metadata
        ?.opplysningsId
}