package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.TelefoninfoTransformer
import no.nav.tps.person.Telefoninfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelefoninfoTransformerTest {

    @Test
    fun gittTelefoninfi_skalFaaTelefoninfo() {
        val inbound = Telefoninfo(jobb = "1234", landkodeJobb = "+47", mobil = "99887767", privat = "22334455")

        val actual = TelefoninfoTransformer.toOutbound(inbound)

        assertEquals(inbound.jobb, actual.jobb)
        assertEquals(inbound.mobil, actual.mobil)
        assertEquals(inbound.privat, actual.privat)
        assertEquals(inbound.landkodeJobb, actual.landkodeJobb)
        assertNull(actual.landkodeMobil)
        assertNull(actual.landkodePrivat)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = Telefoninfo(jobb = null, mobil = null, privat = null)

        val actual = TelefoninfoTransformer.toOutbound(inbound)

        assertEquals(inbound.jobb, actual.jobb)
        assertEquals(inbound.mobil, actual.mobil)
        assertEquals(inbound.privat, actual.privat)
    }
}
