package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT


@Serializable
@SerialName("UTENLANDSK_ADRESSE_I_FRITT_FORMAT")
data class UtenlandskAdresseIFrittFormat(
    val adresselinje1: String?,
    val adresselinje2: String?,
    val adresselinje3: String?,
    val postkode: String?,
    val byEllerStedsnavn: String?,
    val landkode: String,
    val land: String?
) : Adresse {
    override val type: AdresseType get() = UTENLANDSK_ADRESSE_I_FRITT_FORMAT
}