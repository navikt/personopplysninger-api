package no.nav.personopplysninger.consumer.kodeverk.dto

data class Kode(
        val navn: String,
        val betydninger: List<Betydning>
)
