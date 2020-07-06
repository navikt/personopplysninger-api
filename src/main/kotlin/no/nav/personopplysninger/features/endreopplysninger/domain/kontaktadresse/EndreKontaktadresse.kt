package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.endreopplysninger.domain.EndringsType
import no.nav.personopplysninger.features.endreopplysninger.domain.Personopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.opphoer.OpphoerPersonopplysning

private const val OPPLYSNINGSTYPE = "KONTAKTADRESSE"

class EndreKontaktadresse(
        ident: String,
        endringsType: EndringsType,
        endringsMelding: Kontaktadresse,
        opplysningsId: String? = null)
    : Personopplysning<Kontaktadresse>(ident, endringsType, OPPLYSNINGSTYPE, endringsMelding, opplysningsId)

fun slettKontaktadressePayload(ident: String, opplysningsId: String) : OpphoerPersonopplysning {
    return OpphoerPersonopplysning(ident, OPPLYSNINGSTYPE, opplysningsId)
}

fun endreKontaktadressePayload(ident: String, endringsMelding: Kontaktadresse): EndreKontaktadresse {
    return EndreKontaktadresse(ident, EndringsType.OPPRETT, endringsMelding)
}