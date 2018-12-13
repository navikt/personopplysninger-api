package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.MatrikkeladresseTransformer
import no.nav.tps.person.Matrikkeladresse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class MatrikkeladresseTransformerTest {

    @Test
    fun gittMatrikkeladress_skalFaaMatrikkeladresse() {
        val inbound = Matrikkeladresse(bruksnummer = "5002", festenummer = "34", gaardsnummer = "2", undernummer = "23")

        val actual = MatrikkeladresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bruksnummer, actual.bruksnummer)
        assertEquals(inbound.festenummer, actual.festenummer)
        assertEquals(inbound.gaardsnummer, actual.gaardsnummer)
        assertEquals(inbound.undernummer, actual.undernummer)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = Matrikkeladresse(bruksnummer = null, festenummer = null, gaardsnummer = null, undernummer = null)

        val actual = MatrikkeladresseTransformer.toOutbound(inbound)

        assertEquals(inbound.bruksnummer, actual.bruksnummer)
        assertEquals(inbound.festenummer, actual.festenummer)
        assertEquals(inbound.gaardsnummer, actual.gaardsnummer)
        assertEquals(inbound.undernummer, actual.undernummer)
    }
}