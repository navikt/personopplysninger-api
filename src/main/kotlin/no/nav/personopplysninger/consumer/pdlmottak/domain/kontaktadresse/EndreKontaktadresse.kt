package no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse

import no.nav.personopplysninger.consumer.pdlmottak.domain.EndringsType
import no.nav.personopplysninger.consumer.pdlmottak.domain.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.domain.opphoer.OpphoerPersonopplysning

private const val OPPLYSNINGSTYPE = "KONTAKTADRESSE"

class EndreKontaktadresse(
        ident: String,
        endringstype: EndringsType,
        endringsmelding: Kontaktadresse,
        opplysningsid: String? = null)
    : Personopplysning<Kontaktadresse>(ident, endringstype, OPPLYSNINGSTYPE, endringsmelding, opplysningsid)

fun slettKontaktadressePayload(ident: String, opplysningsId: String) : OpphoerPersonopplysning {
    return OpphoerPersonopplysning(ident, OPPLYSNINGSTYPE, opplysningsId)
}