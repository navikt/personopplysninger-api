package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsforholdDto (

        val arbeidsforholdId: Long? = null,
        val type: String? = null,
        val sistBekreftet : String? = null,
        val arbeidsgiver : ArbeidsgiverDto? = null,
        val ansettelsesPeriode : PeriodeDto? = null,
        val utenlandsopphold: ArrayList<UtenlandsoppholdDto>? = null,
        val permisjonPermittering: ArrayList<PermisjonPermitteringDto>? = null,
        val arbeidsavtaler: ArrayList<ArbeidsavtaleDto>? = null
)