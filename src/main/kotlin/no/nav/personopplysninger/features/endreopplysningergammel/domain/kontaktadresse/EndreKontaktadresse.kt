package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysningergammel.domain.EndringsType
import no.nav.personopplysninger.features.endreopplysningergammel.domain.Personopplysning
import no.nav.personopplysninger.features.endreopplysningergammel.domain.opphoer.OpphoerPersonopplysning

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

fun endreKontaktadressePayload(ident: String, endringsMelding: Kontaktadresse): EndreKontaktadresse {
    return EndreKontaktadresse(ident, EndringsType.OPPRETT, endringsMelding)
}