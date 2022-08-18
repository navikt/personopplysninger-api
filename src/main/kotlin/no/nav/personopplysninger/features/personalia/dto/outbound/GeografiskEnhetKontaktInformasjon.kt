package no.nav.personopplysninger.features.personalia.dto.outbound

import kotlinx.serialization.Serializable


@Serializable
data class GeografiskEnhetKontaktInformasjon (
        val postadresse: NavkontorPostadresse,
        val publikumsmottak: List<PublikumsmottakDto>,
        val tlfperson: String? = null,
        val spesielleopplysninger: String? = null
)
