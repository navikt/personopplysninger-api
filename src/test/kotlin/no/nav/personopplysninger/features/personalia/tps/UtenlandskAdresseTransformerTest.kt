package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.UtenlandskAdresseTransformer
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class UtenlandskAdresseTransformerTest {

    @Test
    fun gittUtenlandskAdresse_skalFaaUtenlandskAdresse() {
        val inbound = UtenlandskAdresseObjectMother.utenlandskTestadresse()

        val actual = UtenlandskAdresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse1!!, actual.adresse1)
        assertEquals(inbound.adresse2!!, actual.adresse2)
        assertEquals(inbound.adresse3!!, actual.adresse3)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals(inbound.datoTilOgMed!!, actual.datoTilOgMed)
        assertEquals("KAPP VERDE", actual.land)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = UtenlandskAdresseObjectMother.utenslandskAdresseNullObject()

        val actual = UtenlandskAdresseTransformer.toOutbound(inbound)

        assertNull(actual.adresse1)
        assertNull(actual.adresse2)
        assertNull(actual.adresse3)
        assertNull(actual.datoFraOgMed)
        assertNull(actual.datoTilOgMed)
        assertNull(actual.land)
    }
}
