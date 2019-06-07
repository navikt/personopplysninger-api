package no.nav.personopplysninger.features.personalia.dto.outbound


data class GeografiskEnhetKontaktInformasjon (
        val gateadresse: String? = null,
        val poststed: String? = null,
        val publikumsmottak: List<PublikumsmottakDto>,
        val andre: String? = null,
        val tlfperson: String? = null,
        val spesielleopplysninger: String? = null
)
