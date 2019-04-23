package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsgiver
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsgiverDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.ArbeidsgiverTransformer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class ArbeidsgiverTransformerTest

    @Test
    fun skalFaArbeidsgiver() {
        val inbound: Arbeidsgiver = ArbeidsgiverObjectMother.withDummyValues

        val actual: ArbeidsgiverDto = ArbeidsgiverTransformer.toOutbound(inbound)
        assertNotNull(actual)
        assertEquals(inbound.type.toString(), actual.type)

    }






