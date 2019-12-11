package no.nav.personopplysninger.features.medl.domain

data class Studieinformasjon (
        val delstudie: Boolean,
        val soeknedInnvilget: Boolean,
        val statsborgerland: String,
        val studieland: String?
)
