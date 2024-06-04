package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE

@Serializable
@SerialName("VEGADRESSE")
data class Vegadresse(
    val husnummer: String?,
    val husbokstav: String?,
    val bruksenhetsnummer: String?,
    val adressenavn: String?,
    val kommune: String?,
    val tilleggsnavn: String?,
    val postnummer: String?,
    val poststed: String?
) : Adresse {
    override val type: AdresseType get() = VEGADRESSE
}

