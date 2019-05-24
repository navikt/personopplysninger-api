package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsavtaleDto (

        val antallTimerPrUke: Double? = null,
        val arbeidstidsOrdning: String? = null,
        val sisteStillingsendring : String? = null,
        val sisteLoennsendring : String? = null,
        val yrke : String? = null,
        val gyldighetsperiode: PeriodeDto? = null,
        val stillingsprosent: Double? = null
)