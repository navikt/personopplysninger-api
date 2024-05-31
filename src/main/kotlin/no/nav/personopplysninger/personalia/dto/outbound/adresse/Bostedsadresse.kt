package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.pdl.generated.dto.Date
import no.nav.pdl.generated.dto.DateTime

@Serializable
data class Bostedsadresse(
    val angittFlyttedato: Date? = null,
    val gyldigFraOgMed: DateTime?,
    val gyldigTilOgMed: DateTime?,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse
)