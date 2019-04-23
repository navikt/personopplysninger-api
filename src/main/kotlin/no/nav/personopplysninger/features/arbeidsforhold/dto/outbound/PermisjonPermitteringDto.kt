package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class PermisjonPermitteringDto (

        var periode: PeriodeDto? = null,
        var type: String? = null,
        var prosent: String? = null
)