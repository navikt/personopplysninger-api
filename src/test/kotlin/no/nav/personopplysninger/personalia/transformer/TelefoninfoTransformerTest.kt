package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.Telefonnummer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TelefoninfoTransformerTest {

    @Test
    fun gittTelefoninfi_skalFaaTelefoninfo() {
        val inbound = listOf(
            Telefonnummer("+47", "99887767", 1),
            Telefonnummer("+47", "22334455", 2)
        )

        val actual = inbound.toTlfnr()

        assertEquals(inbound.find { it.prioritet == 1 }?.nummer, actual.telefonHoved)
        assertEquals(inbound.find { it.prioritet == 1 }?.landskode, actual.landskodeHoved)
        assertEquals(inbound.find { it.prioritet == 2 }?.nummer, actual.telefonAlternativ)
        assertEquals(inbound.find { it.prioritet == 2 }?.landskode, actual.landskodeAlternativ)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = emptyList<Telefonnummer>()

        val actual = inbound.toTlfnr()

        assertNull(actual.telefonHoved)
        assertNull(actual.landskodeHoved)
        assertNull(actual.telefonAlternativ)
        assertNull(actual.landskodeAlternativ)
    }
}
