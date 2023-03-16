package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable

@Serializable
data class PublikumsmottakDto(
    var gateadresse: String? = null,
    var poststed: String? = null,
    var husnummer: String? = null,
    var husbokstav: String? = null,
    var postnummer: String? = null,
    var stedsbeskrivelse: String? = null,
    var aapningstider: List<Aapningstid>,
    var spesielleAapningstider: List<Aapningstid>
)
