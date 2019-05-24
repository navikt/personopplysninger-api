package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Arbeidsavtale (

    /* Antall timer per uke */
    val antallTimerPrUke: kotlin.Double? = null,
    /* Arbeidstidsordning (kodeverk: Arbeidstidsordninger) */
    val arbeidstidsordning: kotlin.String? = null,
    /* Avl&oslash;nningstype (kodeverk: Avloenningstyper) */
    val avloenningstype: kotlin.String? = null,
    /* Beregnet antall timer per uke */
    val beregnetAntallTimerPrUke: kotlin.Double? = null,
    /* Beregnet stillingsprosent */
    val beregnetStillingsprosent: kotlin.Double? = null,
    val bruksperiode: Bruksperiode? = null,
    val gyldighetsperiode: Gyldighetsperiode? = null,
    val sisteLoennsendring: kotlin.String? = null,
    val sisteStillingsendring: kotlin.String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Stillingsprosent */
    val stillingsprosent: kotlin.Double? = null,
    /* Yrke (kodeverk: Yrker) */
    val yrke: kotlin.String? = null
)