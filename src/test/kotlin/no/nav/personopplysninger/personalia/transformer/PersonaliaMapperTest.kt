package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.Telefonnummer
import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.personalia.extensions.stringValue
import no.nav.personopplysninger.personalia.transformer.testdata.defaultKonto
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPerson
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PersonaliaMapperTest {

    @Test
    fun gittPersonalia_skalFaaPersonalia() {
        val konto = defaultKonto
        val kodeverk = defaultPersonaliaKodeverk
        val pdlPerson = defaultPerson
        val outbound: Personalia = pdlPerson.toOutboundPersonalia(konto, kodeverk)

        val pdlNavn = pdlPerson.navn.first()
        val pdlFolkeregisteridentifikator = pdlPerson.folkeregisteridentifikator.first()
        val pdlSivilstand = pdlPerson.sivilstand.first().type.stringValue
        val pdlKjoenn = pdlPerson.kjoenn.first().kjoenn?.stringValue

        assertEquals(pdlNavn.let { "${it.fornavn} ${it.mellomnavn}" }, outbound.fornavn)
        assertEquals(pdlNavn.etternavn, outbound.etternavn)
        assertEquals(pdlFolkeregisteridentifikator.identifikasjonsnummer, outbound.personident?.verdi)
        assertEquals(pdlFolkeregisteridentifikator.type, outbound.personident?.type)
        assertTlfnr(pdlPerson.telefonnummer, outbound.tlfnr)
        assertUtenlandskBank(konto, outbound.utenlandskbank!!, kodeverk)
        assertEquals(kodeverk.statsborgerskaptermer, outbound.statsborgerskap)
        assertEquals("${kodeverk.foedekommuneterm}, ${kodeverk.foedelandterm}", outbound.foedested)
        assertEquals(pdlSivilstand, outbound.sivilstand)
        assertEquals(pdlKjoenn, outbound.kjoenn)
    }

    private fun assertTlfnr(expected: List<Telefonnummer>, outbound: Tlfnr?) {
        assertEquals(expected.find { it.prioritet == 1 }?.nummer, outbound?.telefonHoved)
        assertEquals(expected.find { it.prioritet == 2 }?.nummer, outbound?.telefonAlternativ)
    }

    private fun assertUtenlandskBank(
        inbound: Konto,
        outbound: UtenlandskBankInfo,
        kodeverk: PersonaliaKodeverk
    ) {
        val utenlandskBank = inbound.utenlandskKontoInfo!!

        assertEquals(utenlandskBank.bankadresse1, outbound.adresse1)
        assertEquals(utenlandskBank.bankadresse2, outbound.adresse2)
        assertEquals(utenlandskBank.bankadresse3, outbound.adresse3)
        assertEquals(utenlandskBank.bankkode, outbound.bankkode)
        assertEquals(utenlandskBank.banknavn, outbound.banknavn)
        assertEquals(inbound.kontonummer, outbound.kontonummer)
        assertEquals(kodeverk.utenlandskbanklandterm, outbound.land)
        assertEquals(utenlandskBank.swiftBicKode, outbound.swiftkode)
        assertEquals(kodeverk.utenlandskbankvalutaterm, outbound.valuta)
    }
}