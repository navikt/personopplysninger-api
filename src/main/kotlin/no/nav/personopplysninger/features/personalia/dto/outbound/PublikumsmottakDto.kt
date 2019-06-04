package no.nav.personopplysninger.features.personalia.dto.outbound

data class PublikumsmottakDto (

        val gateadresse: String? = null,
        val poststed: String? = null,
        val aapningmandag: Aapningstid? = null,
        val aapningtirsdag: Aapningstid? = null,
        val aapningonsdag: Aapningstid? = null,
        val aapningtorsdag: Aapningstid? = null,
        val aapningfredag: Aapningstid? = null

)
