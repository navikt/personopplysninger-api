package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE

@Serializable
@SerialName("POSTBOKSADRESSE")
data class Postboksadresse(
    val postbokseier: String?,
    val postboks: String,
    val postnummer: String?,
    val poststed: String?
) : Adresse {
    override val type: AdresseType get() = POSTBOKSADRESSE
}