package no.nav.personopplysninger.personalia.dto.outbound.adresse

import kotlinx.serialization.Serializable
import no.nav.pdl.generated.dto.DateTime

@Serializable
data class Oppholdsadresse(
    val oppholdAnnetSted: String?,
    val gyldigFraOgMed: DateTime?,
    val gyldigTilOgMed: DateTime?,
    val coAdressenavn: String?,
    val kilde: String?,
    val adresse: Adresse?
)