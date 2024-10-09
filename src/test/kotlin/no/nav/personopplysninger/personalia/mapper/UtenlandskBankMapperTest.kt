package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.personalia.mapper.testdata.defaultKonto
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class UtenlandskBankMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val outbound: UtenlandskBankInfo = defaultKonto.toOutbound(defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            adresse1 shouldBe "Adresselinje 1"
            adresse2 shouldBe "Adresselinje 2"
            adresse3 shouldBe "Adresselinje 3"
            bankkode shouldBe "bankkode"
            banknavn shouldBe "banknavn"
            kontonummer shouldBe "dummyKontonummer"
            land shouldBe "utenlandskbanklandterm"
            swiftkode shouldBe "swiftBicKode"
            valuta shouldBe "utenlandskbankvalutaterm"
        }
    }
}
