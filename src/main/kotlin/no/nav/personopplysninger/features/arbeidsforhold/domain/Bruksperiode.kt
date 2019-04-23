package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Bruksperiode (

    /* Fra-tidsstempel for bruksperiode, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val fom: kotlin.String? = null,
    /* Til-tidsstempel for bruksperiode, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val tom: kotlin.String? = null
) {
}