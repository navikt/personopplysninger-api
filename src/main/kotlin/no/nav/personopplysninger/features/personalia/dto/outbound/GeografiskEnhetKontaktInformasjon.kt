package no.nav.personopplysninger.features.personalia.dto.outbound


data class GeografiskEnhetKontaktInformasjon (
        val postadresse: NavkontorPostadresse,
        val publikumsmottak: List<PublikumsmottakDto>,
        val tlfperson: String? = null,
        val spesielleopplysninger: String? = null
)
