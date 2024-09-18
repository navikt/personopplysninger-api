package no.nav.personopplysninger.kontaktinformasjon.transformer

import no.nav.personopplysninger.kontaktinformasjon.transformer.testdata.defaultDigitalKontaktinfo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KontaktinformasjonTransformerTest {

    @Test
    fun gittKontaktinformasjon_skalFaaKontaktinformasjon() {
        val inbound = defaultDigitalKontaktinfo
        val spraakTerm = "Nynorsk"

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)

        assertEquals(inbound.epostadresse, actual.epostadresse)
        assertEquals(inbound.mobiltelefonnummer, actual.mobiltelefonnummer)
        assertEquals(spraakTerm, actual.spraak)
    }

    @Test
    fun gittSpraakNorsk_skalReturnereBokmaal() {
        val inbound = defaultDigitalKontaktinfo
        val spraakTerm = "Norsk"

        val actual = KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)

        assertEquals(inbound.epostadresse, actual.epostadresse)
        assertEquals(inbound.mobiltelefonnummer, actual.mobiltelefonnummer)
        assertEquals("Bokmål", actual.spraak)
    }
}
