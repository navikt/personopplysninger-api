package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsavtale
import no.nav.personopplysninger.features.arbeidsforhold.domain.PermisjonPermittering
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsavtaleDto

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.PermisjonPermitteringDto

object ArbeidsavtaleTransformer {

    fun toOutboundArray(inbound: Array<Arbeidsavtale>?): ArrayList<ArbeidsavtaleDto> {

        var arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            var avtaledto = ArbeidsavtaleDto(
                    antallTimerPrUke = arbeidsavtale.antallTimerPrUke,
                    arbeidstidsOrdning = arbeidsavtale.arbeidstidsordning,
                    sisteStillingsendring = arbeidsavtale.sistStillingsendring,
                    stillingsprosent = arbeidsavtale.stillingsprosent,
                    sisteLoennsendring = arbeidsavtale.sistLoennsendring,
                    yrke = arbeidsavtale.yrke,
                    gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.gyldighetsperiode)
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}
