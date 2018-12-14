package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.BoadresseTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class BoadresseTransformerTest {

    @Test
    fun gittBoadresse_skalFaaBoadresse() {
        val inbound = BoadresseObjectMother.vardeveien7()

        val actual = BoadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse, actual.adresse)
        assertEquals(inbound.kommune, actual.kommune)
        assertNotNull(actual.matrikkeladresse)
        assertEquals(inbound.postnummer, actual.postnummer)
        assertNotNull(actual.veiadresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = BoadresseObjectMother.boadresseNullObject()

        val actual = BoadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse, actual.adresse)
        assertEquals(inbound.kommune, actual.kommune)
        assertNull(actual.matrikkeladresse)
        assertEquals(inbound.postnummer, actual.postnummer)
        assertNull(actual.veiadresse)
    }
}