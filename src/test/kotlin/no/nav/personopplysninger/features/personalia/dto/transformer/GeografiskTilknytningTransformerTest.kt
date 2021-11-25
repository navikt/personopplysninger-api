package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyGeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlGeografiskTilknytning
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class GeografiskTilknytningTransformerTest {

    @Test
    fun gittGeografiskTilknytning_skalFaaGeografiskTilknytning() {
        val land = "land"
        val inbound = createDummyGeografiskTilknytning()
        val kodeverk = PersonaliaKodeverk().apply { gtLandterm = land }

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound, kodeverk)

        assertEquals(inbound.gtBydel!!, actual.bydel)
        assertEquals(inbound.gtKommune!!, actual.kommune)
        assertEquals(land, actual.land)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = PdlGeografiskTilknytning(null, null, null)

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.bydel)
        assertNull(actual.kommune)
        assertNull(actual.land)
    }
}
