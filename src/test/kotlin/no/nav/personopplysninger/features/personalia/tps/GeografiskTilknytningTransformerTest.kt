package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskTilknytningTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class GeografiskTilknytningTransformerTest {

    @Test
    fun gittGeografiskTilknytning_skalFaaGeografiskTilknytning() {
        val inbound = GeografiskTilknytningObjectMother.withValuesInAllFields

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertEquals(inbound.bydel!!, actual.bydel)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals(inbound.kommune!!, actual.kommune)
        assertEquals(" ", actual.land)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = GeografiskTilknytningObjectMother.nullObject

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.bydel)
        assertNull(actual.datoFraOgMed)

    }
}
