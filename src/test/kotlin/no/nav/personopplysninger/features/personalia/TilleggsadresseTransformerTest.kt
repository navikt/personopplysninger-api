package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.TilleggsadresseTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class TilleggsadresseTransformerTest {

    @Test
    fun gittTilleggsadresse_skalFaaBoTilleggsadresse() {
        val inbound = TilleggsadresseObjectMother.allFieldsHaveValues

        val actual = TilleggsadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse1!!, actual.adresse1)
        assertEquals(inbound.adresse2!!, actual.adresse2)
        assertEquals(inbound.adresse3!!, actual.adresse3)
        assertEquals(inbound.bolignummer!!, actual.bolignummer)
        assertEquals(inbound.bydel!!, actual.bydel)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals(inbound.datoTilOgMed!!, actual.datoTilOgMed)
        assertEquals(inbound.gateKode!!, actual.gateKode)
        assertEquals(inbound.husbokstav!!, actual.husbokstav)
        assertEquals(inbound.husnummer!!, actual.husnummer)
        assertEquals(inbound.kommunenummer!!, actual.kommunenummer)
        assertEquals(inbound.postboksanlegg!!, actual.postboksanlegg)
        assertEquals(inbound.postnummer!!, actual.postnummer)
    }


    @Test
    fun gittNull_skalFaaNull() {
        val inbound = TilleggsadresseObjectMother.nullObject

        val actual = TilleggsadresseTransformer.toOutbound(inbound)

        assertNull(actual.adresse1)
        assertNull(actual.adresse2)
        assertNull(actual.adresse3)
        assertNull(actual.bolignummer)
        assertNull(actual.bydel)
        assertNull(actual.datoFraOgMed)
        assertNull(actual.datoTilOgMed)
        assertNull(actual.gateKode)
        assertNull(actual.husbokstav)
        assertNull(actual.husnummer)
        assertNull(actual.kommunenummer)
        assertNull(actual.postboksanlegg)
        assertNull(actual.postnummer)
    }
}