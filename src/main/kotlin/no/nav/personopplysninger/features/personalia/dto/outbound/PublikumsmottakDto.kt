package no.nav.personopplysninger.features.personalia.dto.outbound

data class PublikumsmottakDto (

        var gateadresse: String? = null,
        var poststed: String? = null,
        var aapningMandag: Aapningstid? = null,
        var aapningTirsdag: Aapningstid? = null,
        var aapningOnsdag: Aapningstid? = null,
        var aapningTorsdag: Aapningstid? = null,
        var aapningFredag: Aapningstid? = null

)
