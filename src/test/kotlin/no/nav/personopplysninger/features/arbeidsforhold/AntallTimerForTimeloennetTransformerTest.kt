package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.AntallTimerForTimeloennetTransformer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class AntallTimerForTimeloennetTransformerTest {

    @Test
    fun skalFaaRiktigAntallTimer() {
        val inbound = AntallTimerForTimeloennetObjectMother.withDummyValues
        val actual: AntallTimerForTimeloennetDto = AntallTimerForTimeloennetTransformer.toOutbound(inbound)
        assertNotNull(actual)
        assertEquals(inbound.antallTimer.toString(), actual.antallTimer)
        assertEquals(inbound.periode?.fom, actual.periode?.periodeFra)
        assertEquals(inbound.periode?.tom, actual.periode?.periodeTil)

    }

}




