package no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.KontaktadresseType.POSTADRESSE_I_FRITT_FORMAT

data class PostAdresseIFrittFormat (
        val adresselinje1: String?,
        val adresselinje2: String?,
        val adresselinje3: String?,
        val postnummer: String?,
        val poststed: String?,
        val gyldigFraOgMed: String?,
        val gyldigTilOgMed: String?,
        val coAdressenavn: String?,
        override val kilde: String
): Kontaktadresse {
    override val type: KontaktadresseType get() = POSTADRESSE_I_FRITT_FORMAT
}