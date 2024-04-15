package no.nav.personopplysninger.personalia.consumer.dto

import kotlinx.serialization.Serializable

@Serializable
data class Norg2EnhetKontaktinfo(
    val navn: String,
    val brukerkontakt: Brukerkontakt,
) {

    @Serializable
    data class Brukerkontakt(val publikumsmottak: List<Publikumsmottak>) {

        @Serializable
        data class Publikumsmottak(
            val besoeksadresse: Adresse?,
            val aapningstider: List<Aapningstider>?,
            val stedsbeskrivelse: String?,
            val adkomstbeskrivelse: String?,
        ) {

            @Serializable
            data class Adresse(
                val postnummer: String?,
                val poststed: String?,
                val gatenavn: String?,
                val husnummer: String?,
                val husbokstav: String?,
                val adresseTilleggsnavn: String?,
            )

            @Serializable
            data class Aapningstider(
                val dag: String?,
                val dato: String?,
                val fra: String?,
                val til: String?,
                val kommentar: String?,
                val stengt: String?,
                val kunTimeavtale: String?,
            )
        }
    }
}