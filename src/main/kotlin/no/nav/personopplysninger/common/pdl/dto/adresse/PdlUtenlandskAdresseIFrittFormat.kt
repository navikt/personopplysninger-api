package no.nav.personopplysninger.common.pdl.dto.adresse

import kotlinx.serialization.Serializable

@Serializable
data class PdlUtenlandskAdresseIFrittFormat(
    val adresselinje1: String? = null,
    val adresselinje2: String? = null,
    val adresselinje3: String? = null,
    val postkode: String? = null,
    val byEllerStedsnavn: String? = null,
    val landkode: String
)