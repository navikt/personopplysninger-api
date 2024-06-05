package no.nav.personopplysninger.consumer.pdlmottak.dto.inbound

import kotlinx.serialization.Serializable

@Serializable
data class Personopplysning(
    val ident: String,
    val endringstype: EndringsType,
    val opplysningstype: String,
    val endringsmelding: Endringsmelding,
    val opplysningsId: String? = null
) {
    companion object {
        private const val TELEFONNUMMER = "TELEFONNUMMER"
        private const val KONTAKTADRESSE = "KONTAKTADRESSE"

        fun slettTelefonnummerPayload(ident: String, opplysningsId: String): Personopplysning {
            return Personopplysning(
                ident = ident,
                endringstype = EndringsType.OPPHOER,
                opplysningstype = TELEFONNUMMER,
                endringsmelding = OpphoerEndringsMelding(),
                opplysningsId = opplysningsId
            )
        }

        fun endreTelefonnummerPayload(ident: String, endringsmelding: Telefonnummer): Personopplysning {
            return Personopplysning(
                ident = ident,
                endringstype = EndringsType.OPPRETT,
                opplysningstype = TELEFONNUMMER,
                endringsmelding = endringsmelding
            )
        }

        fun slettKontaktadressePayload(ident: String, opplysningsId: String): Personopplysning {
            return Personopplysning(
                ident = ident,
                endringstype = EndringsType.OPPHOER,
                opplysningstype = KONTAKTADRESSE,
                endringsmelding = OpphoerEndringsMelding(),
                opplysningsId = opplysningsId
            )
        }
    }
}