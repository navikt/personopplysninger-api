package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.GeografiskTilknytning
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyGeografiskTilknytning
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GeografiskTilknytningTransformerTest {

    @Test
    fun gittGeografiskTilknytning_skalFaaGeografiskTilknytning() {
        val land = "land"
        val inbound = createDummyGeografiskTilknytning()
        val kodeverk = PersonaliaKodeverk().apply { gtLandterm = land }

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound!!, kodeverk)

        assertEquals(inbound.gtBydel, actual.bydel)
        assertEquals(inbound.gtKommune, actual.kommune)
        assertEquals(land, actual.land)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = GeografiskTilknytning(null, null, null)

        val actual = GeografiskTilknytningTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.bydel)
        assertNull(actual.kommune)
        assertNull(actual.land)
    }
}
