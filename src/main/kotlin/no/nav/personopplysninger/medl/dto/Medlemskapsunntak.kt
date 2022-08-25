package no.nav.personopplysninger.medl.dto

import kotlinx.serialization.Serializable

@Serializable
data class Medlemskapsunntak(
    var perioder: List<Medlemskapsperiode>
)
