package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.KontonummerTransformer
import no.nav.tps.person.Kontonummer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KontonummerTransformerTest {

    @Test
    fun gittKontonummer_skalFaaKontonummer() {
        val inbound = Kontonummer(nummer = "11110011111")

        val actual = KontonummerTransformer.toOutbound(inbound)

        assertEquals(inbound.nummer, actual.nummer)
    }

    fun gittNull_skalFaaNull() {
        val inbound = Kontonummer(nummer = null)

        val actual = KontonummerTransformer.toOutbound(inbound)

        assertEquals(inbound.nummer, actual.nummer)
    }
}