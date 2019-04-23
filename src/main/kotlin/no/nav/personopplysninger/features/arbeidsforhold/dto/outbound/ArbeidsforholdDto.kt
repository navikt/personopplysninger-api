package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsforholdDto (

        val arbeidsforholdId: String? = null,
        val type: String? = null,
        val sistBekreftet : String? = null,
        val arbeidsgiver : String? = null,
        val ansettelsesPeriode : PeriodeDto? = null,
        val utenlandsopphold: ArrayList<UtenlandsoppholdDto>? = null,
        val permisjonPermittering: ArrayList<PermisjonPermitteringDto>? = null
)