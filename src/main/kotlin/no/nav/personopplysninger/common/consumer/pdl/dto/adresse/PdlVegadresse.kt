package no.nav.personopplysninger.common.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlVegadresse(
    val matrikkelId: Long? = null,
    val husnummer: String? = null,
    val husbokstav: String? = null,
    val bruksenhetsnummer: String? = null,
    val adressenavn: String? = null,
    val kommunenummer: String? = null,
    val bydelsnummer: String? = null,
    val tilleggsnavn: String? = null,
    val postnummer: String? = null,
    val koordinater: PdlKoordinater? = null
)