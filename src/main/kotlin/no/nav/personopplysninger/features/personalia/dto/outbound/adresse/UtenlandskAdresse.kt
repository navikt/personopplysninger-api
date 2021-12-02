package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE

@JsonInclude(NON_NULL)
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