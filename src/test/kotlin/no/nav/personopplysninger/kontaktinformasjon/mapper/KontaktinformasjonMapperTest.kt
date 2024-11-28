package no.nav.personopplysninger.kontaktinformasjon.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.consumer.digdirkrr.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.mapper.testdata.defaultDigitalKontaktinfo
import org.junit.jupiter.api.Test

class KontaktinformasjonMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: DigitalKontaktinformasjon = defaultDigitalKontaktinfo
        val outbound: Kontaktinformasjon = inbound.toOutbound(NYNORSK)

        assertSoftly(outbound) {
            epostadresse shouldBe inbound.epostadresse
            mobiltelefonnummer shouldBe inbound.mobiltelefonnummer
            reservert shouldBe inbound.reservert
            spraak shouldBe NYNORSK
        }
    }

    @Test
    fun `should map Norsk spraak to Bokmål`() {
        val inbound: DigitalKontaktinformasjon = defaultDigitalKontaktinfo
        val outbound: Kontaktinformasjon = inbound.toOutbound(NORSK)

        outbound.spraak shouldBe BOKMAAL
    }

    companion object {
        private const val NYNORSK = "Nynorsk"
        private const val BOKMAAL = "Bokmål"
        private const val NORSK = "Norsk"
    }
}
