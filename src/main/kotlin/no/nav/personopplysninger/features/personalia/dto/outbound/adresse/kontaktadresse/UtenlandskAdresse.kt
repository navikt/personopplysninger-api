package no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class UtenlandskAdresse (
        val adressenavnNummer: String?,
        val bygningEtasjeLeilighet: String?,
        val postboksNummerNavn: String?,
        val postkode: String?,
        val bySted: String?,
        val regionDistriktOmraade: String?,
        val landkode: String,
        val land: String?,
        val gyldigFraOgMed: String?,
        val gyldigTilOgMed: String?,
        val coAdressenavn: String?
)