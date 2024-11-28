package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.extensions.stringValue
import no.nav.personopplysninger.personalia.mapper.testdata.defaultKonto
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPerson
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class PersonaliaMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Person = defaultPerson
        val outbound: Personalia = inbound.toOutbound(defaultKonto, defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            inbound.navn.first().let {
                fornavn shouldBe "${it.fornavn} ${it.mellomnavn}"
                etternavn shouldBe etternavn
            }
            personident.shouldNotBeNull()
            inbound.folkeregisteridentifikator.first().let {
                personident!!.verdi shouldBe it.identifikasjonsnummer
                personident!!.type shouldBe it.type
            }
            kontoregisterStatus shouldBe "SUCCESS"
            tlfnr.shouldNotBeNull()
            inbound.telefonnummer.find { it.prioritet == 1 }.let {
                tlfnr?.telefonHoved shouldBe it?.nummer
                tlfnr?.landskodeHoved shouldBe it?.landskode
            }
            inbound.telefonnummer.find { it.prioritet == 2 }.let {
                tlfnr?.telefonAlternativ shouldBe it?.nummer
                tlfnr?.landskodeAlternativ shouldBe it?.landskode
            }
            defaultPersonaliaKodeverk.let {
                statsborgerskap shouldBe it.statsborgerskaptermer
                foedested shouldBe "${it.foedekommuneterm}, ${it.foedelandterm}"
            }
            sivilstand shouldBe inbound.sivilstand.first().type.stringValue
            kjoenn shouldBe inbound.kjoenn.first().kjoenn!!.stringValue
        }
    }

    @Test
    fun `should map correctly with norsk konto`() {
        val norskKonto = defaultKonto.copy(utenlandskKontoInfo = null)
        val outbound: Personalia = defaultPerson.toOutbound(norskKonto, defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            kontonr shouldBe norskKonto.kontonummer
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