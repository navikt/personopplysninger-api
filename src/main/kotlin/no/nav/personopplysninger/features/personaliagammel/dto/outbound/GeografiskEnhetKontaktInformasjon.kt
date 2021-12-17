package no.nav.personopplysninger.features.personaliagammel.dto.outbound


data class GeografiskEnhetKontaktInformasjon (
        val postadresse: NavkontorPostadresse,
        val publikumsmottak: List<PublikumsmottakDto>,
        val tlfperson: String? = null,
        val spesielleopplysninger: String? = null
)
