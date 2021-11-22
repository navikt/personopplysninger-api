package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED

@JsonInclude(NON_NULL)
data class Ukjentbosted (
        val bostedskommune: String?
): Adresse {
    override val type: AdresseType get() = UKJENTBOSTED
}