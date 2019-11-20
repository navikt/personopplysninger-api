package no.nav.personopplysninger.oppslag.kodeverk.api

data class Kode(
        val navn: String,
        val betydninger: List<Betydning>
)
