package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED

data class Ukjentbosted(
    val bostedskommune: String?
) : Adresse {
    override val type: AdresseType get() = UKJENTBOSTED
}