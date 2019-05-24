package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsavtale
import no.nav.personopplysninger.features.arbeidsforhold.domain.PermisjonPermittering
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsavtaleDto

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.PermisjonPermitteringDto

object ArbeidsavtaleTransformer {

    fun toOutbound(inbound: Arbeidsavtale) = ArbeidsavtaleDto(

            antallTimerPrUke = inbound.antallTimerPrUke.toString(),
            arbeidstidsOrdning = inbound.arbeidstidsordning,
            sisteStillingsendring = inbound.sistStillingsendring,
            sisteLoennsendring = inbound.sistLoennsendring,
            yrke = inbound.yrke,
            gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(inbound.gyldighetsperiode)
    )

    fun toOutboundArray(inbound: Array<Arbeidsavtale>?): ArrayList<ArbeidsavtaleDto> {

        var arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            var avtaledto = ArbeidsavtaleDto(
                    antallTimerPrUke = arbeidsavtale.antallTimerPrUke.toString(),
                    arbeidstidsOrdning = arbeidsavtale.arbeidstidsordning,
                    sisteStillingsendring = arbeidsavtale.sistStillingsendring,
                    sisteLoennsendring = arbeidsavtale.sistLoennsendring,
                    yrke = arbeidsavtale.yrke,
                    gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.gyldighetsperiode)
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}
