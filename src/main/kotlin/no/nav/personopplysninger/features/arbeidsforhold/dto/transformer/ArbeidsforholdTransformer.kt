package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto

object ArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?) = ArbeidsforholdDto(

            arbeidsforholdId = inbound.navArbeidsforholdId,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
            opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
            ansettelsesPeriode = PeriodeTransformer.toOutboundfromAnsettelsesperiode(inbound.ansettelsesperiode),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer),
            arbeidsavtaler = ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler)

    )

}
