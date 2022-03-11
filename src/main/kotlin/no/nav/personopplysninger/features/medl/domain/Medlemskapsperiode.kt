package no.nav.personopplysninger.features.medl.domain

import java.time.LocalDate

data class Medlemskapsperiode (
        val fraOgMed: LocalDate,
        var hjemmel: String,
        val kilde: String,
        var lovvalgsland: String?,
        val medlem: Boolean,
        var studieinformasjon: Studieinformasjon?,
        val tilOgMed: LocalDate?,
        var trygdedekning: String?
)
