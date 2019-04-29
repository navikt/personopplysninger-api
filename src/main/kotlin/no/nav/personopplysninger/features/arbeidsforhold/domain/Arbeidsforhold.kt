package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Arbeidsforhold (

    val ansettelsesperiode: Ansettelsesperiode? = null,
    /* Antall timer med timel&oslash;nn */
    val antallTimerForTimeloennet: kotlin.Array<AntallTimerForTimeloennet>? = null,
    /* Arbeidsavtaler - gjeldende og evt. med historikk */
    val arbeidsavtaler: kotlin.Array<Arbeidsavtale>? = null,
    /* Arbeidsforhold-id fra opplysningspliktig */
    val arbeidsforholdId: kotlin.String? = null,
    val arbeidsgiver: Arbeidsgiver? = null,
    val arbeidstaker: Person? = null,
    /* Er arbeidsforholdet innrapportert via A-Ordningen? */
    val innrapportertEtterAOrdningen: kotlin.Boolean? = null,
    /* Arbeidsforhold-id i AAREG */
    val navArbeidsforholdId: kotlin.Long? = null,
    val opplysningspliktig: OpplysningspliktigArbeidsgiver? = null,
    /* Permisjoner og/eller permitteringer */
    val permisjonPermitteringer: kotlin.Array<PermisjonPermittering>? = null,
    val registrert: kotlin.String? = null,
    /* Tidspunkt for siste bekreftelse av arbeidsforhold, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val sistBekreftet: kotlin.String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Arbeidsforholdtype (kodeverk: Arbeidsforholdtyper) */
    val type: kotlin.String? = null,
    /* Utenlandsopphold */
    val utenlandsopphold: kotlin.Array<Utenlandsopphold>? = null
) {
}