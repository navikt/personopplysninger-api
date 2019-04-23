package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Periode (

    /* Fra-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val fom: kotlin.String? = null,
    /* Til-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val tom: kotlin.String? = null
) {
}