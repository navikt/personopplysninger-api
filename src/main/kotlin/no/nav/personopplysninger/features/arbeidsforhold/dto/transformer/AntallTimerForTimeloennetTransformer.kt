package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.AntallTimerForTimeloennet
import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto

object AntallTimerForTimeloennetTransformer {

    fun toOutbound(inbound: AntallTimerForTimeloennet) = AntallTimerForTimeloennetDto(

            antallTimer = inbound.antallTimer.toString(),
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode)

    )

}
