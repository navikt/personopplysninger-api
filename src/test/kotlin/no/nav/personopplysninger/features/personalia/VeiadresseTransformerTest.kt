package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.VeiadresseTransformer
import kotlin.test.assertEquals
import no.nav.tps.person.Veiadresse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class VeiadresseTransformerTest {

    @Test
    fun gittVeiadresse_skalFaaVeiadresse() {
        val inbound = Veiadresse(bokstav = "A", bolignummer = "H303", gatekode = "123", husnummer = "1")

        val actual = VeiadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bokstav, actual.bokstav)
        assertEquals(inbound.bolignummer, actual.bolignummer)
        assertEquals(inbound.gatekode, actual.gatekode)
        assertEquals(inbound.husnummer, actual.husnummer)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = Veiadresse(bokstav = null, bolignummer = null, gatekode = null, husnummer = null)

        val actual = VeiadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bokstav, actual.bokstav)
        assertEquals(inbound.bolignummer, actual.bolignummer)
        assertEquals(inbound.gatekode, actual.gatekode)
        assertEquals(inbound.husnummer, actual.husnummer)
    }
}