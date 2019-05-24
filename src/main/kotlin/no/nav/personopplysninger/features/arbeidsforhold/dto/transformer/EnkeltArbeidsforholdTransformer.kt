package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto

object EnkeltArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val gyldigarbeidsavtale = gyldigArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler))

        return ArbeidsforholdDto(
                arbeidsforholdId = inbound.navArbeidsforholdId,
                type = inbound.type,
                sistBekreftet = inbound.sistBekreftet,
                arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
                opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
                ansettelsesPeriode = PeriodeTransformer.toOutboundfromAnsettelsesperiode(inbound.ansettelsesperiode),
                arbeidsavtaler = ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler),
                utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
                permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer),
                antallTimerPrUke = gyldigarbeidsavtale?.antallTimerPrUke,
                stillingsProsent = gyldigarbeidsavtale?.stillingsprosent,
                arbeidstidsOrdning = gyldigarbeidsavtale?.arbeidstidsOrdning,
                sisteStillingsEndring = gyldigarbeidsavtale?.sisteStillingsendring,
                sisteLoennsEndring = gyldigarbeidsavtale?.sisteLoennsendring
        )
    }

    fun gyldigArbeidsavtale(inbound: List<ArbeidsavtaleDto>): ArbeidsavtaleDto? {
        for (arbeidsavtale in inbound) {
            if (arbeidsavtale.gyldighetsperiode?.periodeTil == null) {
                return arbeidsavtale
            }
        }
        return null
    }

}
