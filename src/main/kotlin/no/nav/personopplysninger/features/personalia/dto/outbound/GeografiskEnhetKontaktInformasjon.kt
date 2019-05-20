package no.nav.personopplysninger.features.personalia.dto.outbound

data class GeografiskEnhetKontaktInformasjon (
        val enhetsnavn: String? = null,
        val gateadresse: String? = null,
        val poststed: String? = null,
        val aapningmandag: String? = null,
        val aapningtirsdag: String? = null,
        val appningonsdag: String? = null,
        val aapningtorsdag: String? = null,
        val aapningfredag: String? = null,
        val andre: String? = null,
        val tlfperson: String? = null,
        val tlfpensjon: String? = null
)
