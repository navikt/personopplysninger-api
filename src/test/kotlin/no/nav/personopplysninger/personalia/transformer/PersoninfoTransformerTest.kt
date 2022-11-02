package no.nav.personopplysninger.personalia.transformer


import no.nav.personopplysninger.common.pdl.dto.personalia.PdlTelefonnummer
import no.nav.personopplysninger.personalia.consumer.tpsproxy.dto.Personinfo
import no.nav.personopplysninger.personalia.consumer.tpsproxy.dto.UtenlandskBank
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPerson
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPersonInfo
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPersonaliaKodeverk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PersoninfoTransformerTest {

    @Test
    fun gittPersonalia_skalFaaPersonalia() {
        val tpsPerson: Personinfo = createDummyPersonInfo()
        val kodeverk = createDummyPersonaliaKodeverk()
        val pdlPerson = createDummyPerson()
        val actual: Personalia = PersoninfoTransformer.toOutbound(tpsPerson, pdlPerson, kodeverk)

        val pdlNavn = pdlPerson.navn.first()
        val pdlFolkeregisteridentifikator = pdlPerson.folkeregisteridentifikator.first()
        val pdlSivilstand = pdlPerson.sivilstand.first().type.beskrivelse
        val pdlKjoenn = pdlPerson.kjoenn.first().kjoenn?.beskrivelse

        assertEquals(pdlNavn.let { "${it.fornavn} ${it.mellomnavn}" }, actual.fornavn)
        assertEquals(pdlNavn.etternavn, actual.etternavn)
        assertEquals(pdlFolkeregisteridentifikator.identifikasjonsnummer, actual.personident?.verdi)
        assertEquals(pdlFolkeregisteridentifikator.type, actual.personident?.type)
        assertTlfnr(pdlPerson.telefonnummer, actual.tlfnr)
        assertUtenlandskBank(tpsPerson.utenlandskBank, actual.utenlandskbank, kodeverk)
        assertEquals(kodeverk.statsborgerskaptermer, actual.statsborgerskap)
        assertEquals("${kodeverk.foedekommuneterm}, ${kodeverk.foedelandterm}", actual.foedested)
        assertEquals(pdlSivilstand, actual.sivilstand)
        assertEquals(pdlKjoenn, actual.kjoenn)
    }

    private fun assertTlfnr(expected: List<PdlTelefonnummer>, actual: Tlfnr?) {
        assertEquals(expected.find { it.prioritet == 1 }?.nummer, actual?.telefonHoved)
        assertEquals(expected.find { it.prioritet == 2 }?.nummer, actual?.telefonAlternativ)
    }

    private fun assertUtenlandskBank(
        inbound: UtenlandskBank?,
        outbound: UtenlandskBankInfo?,
        kodeverk: PersonaliaKodeverk
    ) {
        assertEquals(inbound?.adresse1, outbound?.adresse1)
        assertEquals(inbound?.adresse2, outbound?.adresse2)
        assertEquals(inbound?.adresse3, outbound?.adresse3)
        assertEquals(inbound?.bankkode, outbound?.bankkode)
        assertEquals(inbound?.banknavn, outbound?.banknavn)
        assertEquals(inbound?.iban, outbound?.iban)
        assertEquals(inbound?.kontonummer, outbound?.kontonummer)
        assertEquals(kodeverk.utenlandskbanklandterm, outbound?.land)
        assertEquals(inbound?.swiftkode, outbound?.swiftkode)
        assertEquals(kodeverk.utenlandskbankvalutaterm, outbound?.valuta)
    }
}