package no.nav.personopplysninger.features.endreopplysninger.domain.telefon

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.personopplysninger.features.endreopplysninger.domain.EndringsType
import no.nav.personopplysninger.features.endreopplysninger.domain.Personopplysning

private const val OPPLYSNINGSTYPE = "TELEFONNUMMER"

@JsonIgnoreProperties(ignoreUnknown = true)
class EndreTelefon(
        ident: String,
        endringsType: EndringsType,
        endringsMelding: Telefonnummer,
        opplysningsId: String? = null)
    : Personopplysning<Telefonnummer>(ident, endringsType, OPPLYSNINGSTYPE, endringsMelding, opplysningsId)

fun slettNummerPayload(ident: String, endringsMelding: Telefonnummer, opplysningsId: String) : EndreTelefon {
    return EndreTelefon(ident, EndringsType.OPPHOER, endringsMelding, opplysningsId)
}

fun endreNummerPayload(ident: String, endringsMelding: Telefonnummer): EndreTelefon {
    return EndreTelefon(ident, EndringsType.OPPRETT, endringsMelding)
}
