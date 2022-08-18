package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.POSTADRESSE_I_FRITT_FORMAT

@Serializable
@SerialName("PostAdresseIFrittFormat")
data class PostAdresseIFrittFormat(
    val adresselinje1: String?,
    val adresselinje2: String?,
    val adresselinje3: String?,
    val postnummer: String?,
    val poststed: String?
) : Adresse {
    override val type: AdresseType get() = POSTADRESSE_I_FRITT_FORMAT
}