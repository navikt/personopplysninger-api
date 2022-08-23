package no.nav.personopplysninger.features.kontaktinformasjon.dto.transformer

import no.nav.personopplysninger.features.kontaktinformasjon.dto.transformer.testdata.createDummyDigitalKontaktinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktinformasjonTransformerTest {

    @Test
    fun gittKontaktinformasjon_skalFaaKontaktinformasjon() {
        val inbound = createDummyDigitalKontaktinfo()
        val spraakTerm = "Nynorsk"

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)

        assertEquals(inbound.epostadresse!!, actual.epostadresse)
        assert(inbound.kanVarsles == actual.kanVarsles!!)
        assertEquals(inbound.mobiltelefonnummer!!, actual.mobiltelefonnummer)
        assertEquals(spraakTerm, actual.spraak)
    }

    @Test
    fun gittSpraakNorsk_skalReturnereBokmaal() {
        val inbound = createDummyDigitalKontaktinfo()
        val spraakTerm = "Norsk"

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)

        assertEquals(inbound.epostadresse!!, actual.epostadresse)
        assert(inbound.kanVarsles == actual.kanVarsles!!)
        assertEquals(inbound.mobiltelefonnummer!!, actual.mobiltelefonnummer)
        assertEquals("Bokmål", actual.spraak)
    }
}
