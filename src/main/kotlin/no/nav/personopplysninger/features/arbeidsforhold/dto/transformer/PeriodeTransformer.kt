package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Ansettelsesperiode
import no.nav.personopplysninger.features.arbeidsforhold.domain.Gyldighetsperiode
import no.nav.personopplysninger.features.arbeidsforhold.domain.Periode
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.PeriodeDto

object PeriodeTransformer {

    fun toOutboundfromPeriode(inbound: Periode?) = PeriodeDto (
            periodeFra = inbound?.fom,
            periodeTil = inbound?.tom
    )

    fun toOutboundfromAnsettelsesperiode(inbound: Ansettelsesperiode?) = PeriodeDto (
            periodeFra = inbound?.periode?.fom,
            periodeTil = inbound?.periode?.tom
    )

    fun toOutboundfromGyldighetsperiode(inbound: Gyldighetsperiode?) = PeriodeDto (
            periodeFra = inbound?.fom,
            periodeTil = inbound?.tom
    )
}
