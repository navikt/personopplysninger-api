package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE

data class UtenlandskAdresse(
    val adressenavnNummer: String?,
    val bygningEtasjeLeilighet: String?,
    val postboksNummerNavn: String?,
    val postkode: String?,
    val bySted: String?,
    val regionDistriktOmraade: String?,
    val landkode: String,
    val land: String?
) : Adresse {
    override val type: AdresseType get() = UTENLANDSK_ADRESSE
}