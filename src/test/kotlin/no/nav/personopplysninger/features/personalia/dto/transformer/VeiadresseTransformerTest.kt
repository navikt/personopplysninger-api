package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.VeiadresseObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class VeiadresseTransformerTest {

    @Test
    fun gittVeiadresse_skalFaaVeiadresse() {
        val inbound = VeiadresseObjectMother.vardeveien7()

        val actual = VeiadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bokstav, actual.bokstav)
        assertEquals(inbound.bolignummer, actual.bolignummer)
        assertEquals(inbound.gatekode, actual.gatekode)
        assertEquals(inbound.husnummer, actual.husnummer)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = VeiadresseObjectMother.veiadresseNullObject()

        val actual = VeiadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bokstav, actual.bokstav)
        assertEquals(inbound.bolignummer, actual.bolignummer)
        assertEquals(inbound.gatekode, actual.gatekode)
        assertEquals(inbound.husnummer, actual.husnummer)
    }
}
