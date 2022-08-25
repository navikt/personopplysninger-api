package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE

@Serializable
@SerialName("UtenlandskAdresse")
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