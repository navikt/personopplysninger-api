package no.nav.personopplysninger.features.medl.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Medlemskapsperiode (
        val fraOgMed: LocalDate,
        val hjemmel: String,
        val kilde: String,
        val lovvalgsland: String?,
        val medlem: Boolean,
        val studieinformasjon: Studieinformasjon?,
        val tilOgMed: LocalDate?,
        val trygdedekning: String?
)
