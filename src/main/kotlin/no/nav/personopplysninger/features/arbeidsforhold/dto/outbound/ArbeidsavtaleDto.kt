package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsavtaleDto (

        val antallTimerPrUke: String? = null,
        val arbeidstidsOrdning: String? = null,
        val sistStillingsEndring : String? = null,
        val sistLoennsEndring : String? = null,
        val yrke : String? = null,
        val gyldighetsperiode: PeriodeDto? = null
)