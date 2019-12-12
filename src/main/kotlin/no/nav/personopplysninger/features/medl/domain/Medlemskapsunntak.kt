package no.nav.personopplysninger.features.medl.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Medlemskapsunntak (
        val dekning: String,
        val fraOgMed: LocalDate,
        val grunnlag: String,
        val helsedel: Boolean,
        val ident: String,
        val lovvalg: String,
        val lovvalgsland: String?,
        val medlem: Boolean,
        val status: String,
        val statusaarsak: String?,
        val tilOgMed: LocalDate,
        val unntakId: Int,
        val sporingsinformasjon: Sporingsinformasjon?,
        val studieinformasjon: Studieinformasjon?
)

