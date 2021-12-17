package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyDigitalKontaktinfoBolkMedFeil
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyDigitalKontaktinfoBolkMedKontaktinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktinformasjonTransformerTest {

    @Test
    fun gittKontaktinformasjon_skalFaaKontaktinformasjon() {
        val inbound = createDummyDigitalKontaktinfoBolkMedKontaktinfo()

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, "12345678")

        assertEquals(inbound.kontaktinfo?.get("12345678")?.epostadresse!!, actual.epostadresse)
        assert(inbound.kontaktinfo?.get("12345678")?.kanVarsles!! == actual.kanVarsles!!)
        assertEquals(inbound.kontaktinfo?.get("12345678")?.mobiltelefonnummer!!, actual.mobiltelefonnummer)
    }

    @Test
    fun gittFeil_skalFaaNull() {
        val inbound = createDummyDigitalKontaktinfoBolkMedFeil()

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, "12345678")

        assertNull(actual.epostadresse)
        assertNull(actual.kanVarsles)
        assertNull(actual.mobiltelefonnummer)
    }
}
