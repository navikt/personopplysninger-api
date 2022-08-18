package no.nav.personopplysninger.consumer.pdlmottak.dto.kontaktadresse

import no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer.OpphoerPersonopplysning

private const val OPPLYSNINGSTYPE = "KONTAKTADRESSE"

fun slettKontaktadressePayload(ident: String, opplysningsId: String): OpphoerPersonopplysning {
    return OpphoerPersonopplysning(ident, OPPLYSNINGSTYPE, opplysningsId)
}