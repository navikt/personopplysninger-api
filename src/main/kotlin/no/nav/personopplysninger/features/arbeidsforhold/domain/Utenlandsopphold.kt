package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Utenlandsopphold (

    /* Landkode (kodeverk: Landkoder) */
    val landkode: kotlin.String? = null,
    val periode: Periode? = null,
    /* Rapporteringsperiode for utenlandsopphold, format (ISO-8601): yyyy-MM */
    val rapporteringsperiode: kotlin.String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null
) {
}