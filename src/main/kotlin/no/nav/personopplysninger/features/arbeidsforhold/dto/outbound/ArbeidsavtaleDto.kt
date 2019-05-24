package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsavtaleDto (

        val antallTimerPrUke: String? = null,
        val arbeidstidsOrdning: String? = null,
        val sisteStillingsendring : String? = null,
        val sisteLoennsendring : String? = null,
        val yrke : String? = null,
        val gyldighetsperiode: PeriodeDto? = null,
        val stillingsprosent: String? = null
)