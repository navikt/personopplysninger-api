package no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse

data class PdlVegadresse(
        val matrikkelId: Long?,
        val husnummer: String?,
        val husbokstav: String?,
        val bruksenhetsnummer: String?,
        val adressenavn: String?,
        val kommunenummer: String?,
        val tilleggsnavn: String?,
        val postnummer: String?,
        val koordinater: PdlKoordinater?
)