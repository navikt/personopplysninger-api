package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.personalia.mapper.testdata.defaultKonto
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class UtenlandskBankMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Konto = defaultKonto
        val outbound: UtenlandskBankInfo = defaultKonto.toOutbound(defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            adresse1 shouldBe inbound.utenlandskKontoInfo!!.bankadresse1
            adresse2 shouldBe inbound.utenlandskKontoInfo!!.bankadresse2
            adresse3 shouldBe inbound.utenlandskKontoInfo!!.bankadresse3
            bankkode shouldBe inbound.utenlandskKontoInfo!!.bankkode
            banknavn shouldBe inbound.utenlandskKontoInfo!!.banknavn
            kontonummer shouldBe inbound.kontonummer
            swiftkode shouldBe inbound.utenlandskKontoInfo!!.swiftBicKode
            land shouldBe defaultPersonaliaKodeverk.utenlandskbanklandterm
            valuta shouldBe defaultPersonaliaKodeverk.utenlandskbankvalutaterm
        }
    }
}
