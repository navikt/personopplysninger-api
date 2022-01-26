package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyDigitalKontaktinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktinformasjonTransformerTest {

    @Test
    fun gittKontaktinformasjon_skalFaaKontaktinformasjon() {
        val inbound = createDummyDigitalKontaktinfo()

        val actual = KontaktinformasjonTransformer.toOutbound(inbound)

        assertEquals(inbound.epostadresse!!, actual.epostadresse)
        assert(inbound.kanVarsles == actual.kanVarsles!!)
        assertEquals(inbound.mobiltelefonnummer!!, actual.mobiltelefonnummer)
    }
}
