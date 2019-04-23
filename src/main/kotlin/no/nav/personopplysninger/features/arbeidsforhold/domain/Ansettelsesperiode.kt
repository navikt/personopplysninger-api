package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Ansettelsesperiode (

    val bruksperiode: Bruksperiode? = null,
    val periode: Periode? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Varslingskode (kodeverk: Varslingskode_5fAa-registeret) - benyttes hvis ansettelsesperiode er lukket maskinelt */
    val varslingskode: kotlin.String? = null
) {
}