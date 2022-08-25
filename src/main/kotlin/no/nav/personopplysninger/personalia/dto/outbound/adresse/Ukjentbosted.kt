package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED

@Serializable
@SerialName("Ukjentbosted")
data class Ukjentbosted(
    val bostedskommune: String?
) : Adresse {
    override val type: AdresseType get() = UKJENTBOSTED
}