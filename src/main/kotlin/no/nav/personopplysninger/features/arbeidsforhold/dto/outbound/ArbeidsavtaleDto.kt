package no.nav.personopplysninger.features.arbeidsforhold.dto.outbound

data class ArbeidsavtaleDto (

        val antallTimerPrUke: String? = null,
        val arbeidstidsOrdning: String? = null,
        val sisteStillingsEndring : String? = null,
        val sisteLoennsEndring : String? = null,
        val yrke : String? = null,
        val gyldighetsperiode: PeriodeDto? = null,
        val stillingsProsent: String? = null
)