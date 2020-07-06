package no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse

data class PostAdresseIFrittFormat (
        val adresselinje1: String?,
        val adresselinje2: String?,
        val adresselinje3: String?,
        val postnummer: String?,
        val poststed: String?,
        val gyldigFraOgMed: String?,
        val gyldigTilOgMed: String?,
        val coAdressenavn: String?
)