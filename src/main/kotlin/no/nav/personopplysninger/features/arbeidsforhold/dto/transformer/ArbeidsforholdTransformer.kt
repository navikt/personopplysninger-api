package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto

object ArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?) = ArbeidsforholdDto(

            arbeidsforholdId = inbound.arbeidsforholdId,
            type = inbound.type,
            sistBekreftet = inbound.sistBekreftet,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
            ansettelsesPeriode = PeriodeTransformer.toOutboundfromAnsettelsesperiode(inbound.ansettelsesperiode),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer)

    )

}
