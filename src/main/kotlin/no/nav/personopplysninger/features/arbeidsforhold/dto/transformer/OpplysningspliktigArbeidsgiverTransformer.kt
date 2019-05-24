package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsgiver
import no.nav.personopplysninger.features.arbeidsforhold.domain.OpplysningspliktigArbeidsgiver

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsgiverDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.OpplysningspliktigArbeidsgiverDto

object OpplysningspliktigArbeidsgiverTransformer {

    fun toOutbound(inbound: OpplysningspliktigArbeidsgiver?, arbgivnavn: String?) = OpplysningspliktigArbeidsgiverDto(

            type = inbound?.type,
            orgnr = inbound?.organisasjonsnummer,
            orgnavn = arbgivnavn

    )
}
