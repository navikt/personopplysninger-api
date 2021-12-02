package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT


data class UtenlandskAdresseIFrittFormat (
        val adresselinje1: String?,
        val adresselinje2: String?,
        val adresselinje3: String?,
        val postkode: String?,
        val byEllerStedsnavn: String?,
        val landkode: String,
        val land: String?,
        override val kilde: String?
): Kontaktadresse {
    override val type: KontaktadresseType get() = UTENLANDSK_ADRESSE_I_FRITT_FORMAT
}