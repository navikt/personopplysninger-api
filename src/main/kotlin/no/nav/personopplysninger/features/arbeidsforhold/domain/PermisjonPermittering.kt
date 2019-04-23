package no.nav.personopplysninger.features.arbeidsforhold.domain

data class PermisjonPermittering (

    val periode: Periode? = null,
    /* Id fra opplysningspliktig */
    val permisjonPermitteringId: kotlin.String? = null,
    /* Prosent for permisjon eller permittering */
    val prosent: kotlin.Double? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Permisjon-/permitteringstype (kodeverk: PermisjonsOgPermitteringsBeskrivelse) */
    val type: kotlin.String? = null
) {
}