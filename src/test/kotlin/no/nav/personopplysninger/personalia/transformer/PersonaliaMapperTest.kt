package no.nav.personopplysninger.personalia.transformer

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.transformer.testdata.defaultKonto
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPerson
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class PersonaliaMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val outbound: Personalia = defaultPerson.toOutbound(defaultKonto, defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            fornavn shouldBe "fornavn mellomnavn"
            etternavn shouldBe "etternavn"
            personident.shouldNotBeNull()
            personident!!.verdi shouldBe "identifikasjonsnummer"
            personident!!.type shouldBe "type"
            kontoregisterStatus shouldBe "SUCCESS"
            tlfnr.shouldNotBeNull()
            tlfnr?.telefonAlternativ shouldBe "22113344"
            tlfnr?.landskodeAlternativ shouldBe "+47"
            tlfnr?.telefonHoved shouldBe "97505050"
            tlfnr?.landskodeHoved shouldBe "+47"
            statsborgerskap shouldHaveSize 1
            statsborgerskap shouldContain "statsborgerskapterm"
            foedested shouldBe "foedekommuneterm, foedelandterm"
            sivilstand shouldBe "Gift"
            kjoenn shouldBe "Kvinne"
        }
    }

    @Test
    fun `should map correctly with norsk konto`() {
        val norskKonto = defaultKonto.copy(utenlandskKontoInfo = null)
        val outbound: Personalia = defaultPerson.toOutbound(norskKonto, defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            kontonr shouldBe "dummyKontonummer"
            utenlandskbank.shouldBeNull()
        }
    }

    @Test
    fun `should map correctly with utenlandsk konto`() {
        val utenlandskKonto = defaultKonto
        val outbound: Personalia = defaultPerson.toOutbound(utenlandskKonto, defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            kontonr.shouldBeNull()
            utenlandskbank.shouldNotBeNull()
        }
    }
}