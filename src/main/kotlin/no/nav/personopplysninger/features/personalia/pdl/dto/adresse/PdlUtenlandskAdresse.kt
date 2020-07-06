package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

data class PdlUtenlandskAdresse (
    val adressenavnNummer: String?,
    val bygningEtasjeLeilighet: String?,
    val postboksNummerNavn: String?,
    val postkode: String?,
    val bySted: String?,
    val regionDistriktOmraade: String?,
    val landkode: String
)