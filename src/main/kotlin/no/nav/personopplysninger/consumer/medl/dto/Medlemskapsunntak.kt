package no.nav.personopplysninger.consumer.medl.dto

import kotlinx.serialization.Serializable

@Serializable
data class Medlemskapsunntak(
    var perioder: List<Medlemskapsperiode>
)
