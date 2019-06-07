package no.nav.personopplysninger.features.personalia.dto.outbound

data class PublikumsmottakDto (

        var gateadresse: String? = null,
        var poststed: String? = null,
        var aapningmandag: Aapningstid? = null,
        var aapningtirsdag: Aapningstid? = null,
        var aapningonsdag: Aapningstid? = null,
        var aapningtorsdag: Aapningstid? = null,
        var aapningfredag: Aapningstid? = null

)
