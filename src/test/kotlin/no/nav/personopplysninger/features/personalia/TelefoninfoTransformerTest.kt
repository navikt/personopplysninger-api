package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.TelefoninfoTransformer
import no.nav.tps.person.Telefoninfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelefoninfoTransformerTest {

    @Test
    fun gittTelefoninfi_skalFaaTelefoninfo() {
        val inbound = Telefoninfo(jobb = "1234", mobil = "99887767", privat = "22334455")

        val actual = TelefoninfoTransformer.toOutbound(inbound)

        assertEquals(inbound.jobb, actual.jobb)
        assertEquals(inbound.mobil, actual.mobil)
        assertEquals(inbound.privat, actual.privat)
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