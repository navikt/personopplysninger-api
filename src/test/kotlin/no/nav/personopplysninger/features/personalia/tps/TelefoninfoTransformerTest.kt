package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.toTlfnr
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.features.personalia.pdl.dto.personalia.PdlTelefonnummer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TelefoninfoTransformerTest {

    @Test
    fun gittTelefoninfi_skalFaaTelefoninfo() {
        val inbound = PdlPerson(
                telefonnummer = listOf(
                        PdlTelefonnummer("+47", "99887767", 1, "123"),
                        PdlTelefonnummer("+47", "22334455", 2, "123")
                )
        )

        val actual = inbound.telefonnummer.toTlfnr()

        assertEquals(inbound.telefonnummer.find { it.prioritet == 1 }?.nummer, actual.telefonHoved)
        assertEquals(inbound.telefonnummer.find { it.prioritet == 1 }?.landskode, actual.landskodeHoved)
        assertEquals(inbound.telefonnummer.find { it.prioritet == 2 }?.nummer, actual.telefonAlternativ)
        assertEquals(inbound.telefonnummer.find { it.prioritet == 2 }?.landskode, actual.landskodeAlternativ)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = PdlPerson(telefonnummer = emptyList())

        val actual = inbound.telefonnummer.toTlfnr()

        assertNull(actual.telefonHoved)
        assertNull(actual.landskodeHoved)
        assertNull(actual.telefonAlternativ)
        assertNull(actual.landskodeAlternativ)
    }
}
