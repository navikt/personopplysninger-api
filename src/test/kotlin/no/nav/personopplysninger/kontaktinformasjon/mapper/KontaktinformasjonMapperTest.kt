package no.nav.personopplysninger.kontaktinformasjon.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.mapper.testdata.defaultDigitalKontaktinfo
import org.junit.jupiter.api.Test

class KontaktinformasjonMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val outbound: Kontaktinformasjon = defaultDigitalKontaktinfo.toOutbound("Nynorsk")

        assertSoftly(outbound) {
            epostadresse shouldBe "hurra@test.no"
            mobiltelefonnummer shouldBe "12345678"
            reservert shouldBe false
            spraak shouldBe "Nynorsk"
        }
    }

    @Test
    fun `should map Norsk spraak to Bokmål`() {
        val outbound: Kontaktinformasjon = defaultDigitalKontaktinfo.toOutbound("Norsk")

        outbound.spraak shouldBe "Bokmål"
    }
}
