package no.nav.personopplysninger.consumer.kodeverk.domain

data class Kode(
        val navn: String,
        val betydninger: List<Betydning>
)
