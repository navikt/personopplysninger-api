package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsgiver
import no.nav.personopplysninger.features.arbeidsforhold.domain.OpplysningspliktigArbeidsgiver

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsgiverDto

object ArbeidsgiverTransformer {

    fun toOutbound(inbound: Arbeidsgiver?, arbgivnavn: String?) = ArbeidsgiverDto(

            type = inbound?.type,
            orgnr = inbound?.organisasjonsnummer,
            orgnavn = arbgivnavn

    )
}
