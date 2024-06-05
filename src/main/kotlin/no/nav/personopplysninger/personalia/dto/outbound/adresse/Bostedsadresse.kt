package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.pdl.generated.dto.Date

@Serializable
data class Bostedsadresse(
    val angittFlyttedato: Date? = null,
    val coAdressenavn: String?,
    val adresse: Adresse
)