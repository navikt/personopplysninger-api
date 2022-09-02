package no.nav.personopplysninger.endreopplysninger.dto.inbound

import kotlinx.serialization.Serializable

private const val TELEFONNUMMER = "TELEFONNUMMER"
private const val KONTAKTADRESSE = "KONTAKTADRESSE"

@Serializable
class Personopplysning(
    val ident: String,
    val endringstype: EndringsType,
    val opplysningstype: String,
    val endringsmelding: Endringsmelding,
    val opplysningsId: String? = null
) {
    fun asSingleEndring(): PersonEndring {
        return PersonEndring(personopplysninger = listOf(this))
    }
}

fun slettNummerPayload(ident: String, opplysningsId: String): Personopplysning {
    return Personopplysning(
        ident,
        EndringsType.OPPHOER, TELEFONNUMMER,
        OpphoerEndringsMelding(), opplysningsId
    )
}

fun endreNummerPayload(ident: String, endringsMelding: Telefonnummer): Personopplysning {
    return Personopplysning(ident, EndringsType.OPPRETT, TELEFONNUMMER, endringsMelding)
}

fun slettKontaktadressePayload(ident: String, opplysningsId: String): Personopplysning {
    return Personopplysning(
        ident,
        EndringsType.OPPHOER, KONTAKTADRESSE,
        OpphoerEndringsMelding(), opplysningsId
    )
}