package no.nav.personopplysninger.consumer.pdl.dto.adresse

data class PdlVegadresse(
        val matrikkelId: Long?,
        val husnummer: String?,
        val husbokstav: String?,
        val bruksenhetsnummer: String?,
        val adressenavn: String?,
        val kommunenummer: String?,
        val bydelsnummer: String?,
        val tilleggsnavn: String?,
        val postnummer: String?,
        val koordinater: PdlKoordinater?
)