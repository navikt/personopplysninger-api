package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.PostadresseTransformer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PostadresseTransformerTest {

    @Test
    fun gitPostadresse_skalFaaPostadresse() {
        val inbound = PostadresseObjectMother.testPostadresse()

        val actual = PostadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse1!!, actual.adresse1)
        assertEquals(inbound.adresse2!!, actual.adresse2)
        assertEquals(inbound.adresse3!!, actual.adresse3)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals(inbound.land!!, actual.land)
        assertEquals(inbound.postnummer!!, actual.postnummer)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = PostadresseObjectMother.postadresseNullObject()

        val actual = PostadresseTransformer.toOutbound(inbound)

        assertNull(actual.adresse1)
        assertNull(actual.adresse2)
        assertNull(actual.adresse3)
        assertNull(actual.datoFraOgMed)
        assertNull(actual.land)
        assertNull(actual.postnummer)
    }
}