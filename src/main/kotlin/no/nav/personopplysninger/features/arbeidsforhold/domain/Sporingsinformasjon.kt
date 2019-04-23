package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Sporingsinformasjon (

    /* Brukernavn for endring */
    val endretAv: kotlin.String? = null,
    /* Kilde for endring */
    val endretKilde: kotlin.String? = null,
    /* Kildereferanse for endring */
    val endretKildereferanse: kotlin.String? = null,
    /* Tidspunkt for endring, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val endretTidspunkt: kotlin.String? = null,
    /* Brukernavn for opprettelse */
    val opprettetAv: kotlin.String? = null,
    /* Kilde for opprettelse */
    val opprettetKilde: kotlin.String? = null,
    /* Kildereferanse for opprettelse */
    val opprettetKildereferanse: kotlin.String? = null,
    /* Tidspunkt for opprettelse, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val opprettetTidspunkt: kotlin.String? = null
) {
}