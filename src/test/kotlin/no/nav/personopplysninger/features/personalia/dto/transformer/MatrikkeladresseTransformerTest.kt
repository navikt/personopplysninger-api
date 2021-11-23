package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.MatrikkeladresseObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class MatrikkeladresseTransformerTest {

    @Test
    fun gittMatrikkeladress_skalFaaMatrikkeladresse() {
        val inbound = MatrikkeladresseObjectMother.vardeveien7()
        val actual = MatrikkeladresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bruksnummer, actual.bruksnummer)
        assertEquals(inbound.festenummer, actual.festenummer)
        assertEquals(inbound.gaardsnummer, actual.gaardsnummer)
        assertEquals(inbound.undernummer, actual.undernummer)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = MatrikkeladresseObjectMother.matrikkeladresseNullObject()

        val actual = MatrikkeladresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bruksnummer, actual.bruksnummer)
        assertEquals(inbound.festenummer, actual.festenummer)
        assertEquals(inbound.gaardsnummer, actual.gaardsnummer)
        assertEquals(inbound.undernummer, actual.undernummer)
    }
}
