package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE

@Serializable
@SerialName("MATRIKKELADRESSE")
data class Matrikkeladresse(
    val bruksenhetsnummer: String?,
    val tilleggsnavn: String?,
    val postnummer: String?,
    val poststed: String?,
    val kommunenummer: String?,
    val kommune: String?,
) : Adresse {
    override val type: AdresseType get() = MATRIKKELADRESSE
}