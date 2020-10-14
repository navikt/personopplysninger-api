package no.nav.personopplysninger.features.medl.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Medlemskapsunntak (
        val perioder: List<Medlemskapsperiode>
)
