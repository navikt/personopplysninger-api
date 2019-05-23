package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.PostadresseTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class PostadresseTransformerTest {

    @Test
    fun gittPostadresse_skalFaaPostadresse() {
        val inbound = PostadresseObjectMother.testPostadresse()

        val actual = PostadresseTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertEquals(inbound.adresse1!!, actual.adresse1)
        assertEquals(inbound.adresse2!!, actual.adresse2)
        assertEquals(inbound.adresse3!!, actual.adresse3)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals("", actual.land)
        assertEquals("", actual.poststed)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = PostadresseObjectMother.postadresseNullObject()

        val actual = PostadresseTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.adresse1)
        assertNull(actual.adresse2)
        assertNull(actual.adresse3)
        assertNull(actual.datoFraOgMed)

    }
}
