package no.nav.personopplysninger.consumer.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlUtenlandskAdresse(
    val adressenavnNummer: String? = null,
    val bygningEtasjeLeilighet: String? = null,
    val postboksNummerNavn: String? = null,
    val postkode: String? = null,
    val bySted: String? = null,
    val regionDistriktOmraade: String? = null,
    val landkode: String
)