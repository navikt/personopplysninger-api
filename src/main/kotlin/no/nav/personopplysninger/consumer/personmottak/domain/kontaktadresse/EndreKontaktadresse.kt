package no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse

import no.nav.personopplysninger.consumer.personmottak.domain.EndringsType
import no.nav.personopplysninger.consumer.personmottak.domain.Personopplysning
import no.nav.personopplysninger.consumer.personmottak.domain.opphoer.OpphoerPersonopplysning

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