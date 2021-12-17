package no.nav.personopplysninger.features.personaliagammel.dto.outbound

data class PublikumsmottakDto (

        var gateadresse: String? = null,
        var poststed: String? = null,
        var husnummer: String? = null,
        var husbokstav: String? = null,
        var postnummer: String? = null,
        var stedsbeskrivelse: String? = null,
        var aapningMandag: Aapningstid? = null,
        var aapningTirsdag: Aapningstid? = null,
        var aapningOnsdag: Aapningstid? = null,
        var aapningTorsdag: Aapningstid? = null,
        var aapningFredag: Aapningstid? = null,
        var aapningAndre: List<Aapningstid>? = null

)
