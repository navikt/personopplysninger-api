package no.nav.personopplysninger.consumer.pdlmottak.dto.telefon

import no.nav.personopplysninger.consumer.pdlmottak.dto.EndringsType
import no.nav.personopplysninger.consumer.pdlmottak.dto.Personopplysning
import no.nav.personopplysninger.consumer.pdlmottak.dto.opphoer.OpphoerPersonopplysning

private const val OPPLYSNINGSTYPE = "TELEFONNUMMER"

class EndreTelefon(
        ident: String,
        endringsType: EndringsType,
        endringsMelding: Telefonnummer,
        opplysningsId: String? = null)
    : Personopplysning<Telefonnummer>(ident, endringsType, OPPLYSNINGSTYPE, endringsMelding, opplysningsId)

fun slettNummerPayload(ident: String, opplysningsId: String) : OpphoerPersonopplysning {
    return OpphoerPersonopplysning(ident, OPPLYSNINGSTYPE, opplysningsId)
}

fun endreNummerPayload(ident: String, endringsMelding: Telefonnummer): EndreTelefon {
    return EndreTelefon(ident, EndringsType.OPPRETT, endringsMelding)
}
