package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.consumer.kontoregister.dto.Konto
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlTelefonnummer
import no.nav.personopplysninger.features.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.features.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyKonto
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPerson
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonaliaKodeverk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class PersoninfoTransformerTest {

    @Test
    fun gittPersonalia_skalFaaPersonalia() {
        val konto = createDummyKonto()
        val kodeverk = createDummyPersonaliaKodeverk()
        val pdlPerson = createDummyPerson()
        val actual: Personalia = PersoninfoTransformer.toOutbound(pdlPerson, konto, kodeverk)

        val pdlNavn = pdlPerson.navn.first()
        val pdlFolkeregisteridentifikator = pdlPerson.folkeregisteridentifikator.first()
        val pdlSivilstand = pdlPerson.sivilstand.first().type.beskrivelse
        val pdlKjoenn = pdlPerson.kjoenn.first().kjoenn?.beskrivelse

        assertEquals(pdlNavn.let { "${it.fornavn} ${it.mellomnavn}" }, actual.fornavn!!)
        assertEquals(pdlNavn.etternavn, actual.etternavn!!)
        assertEquals(pdlFolkeregisteridentifikator.identifikasjonsnummer, actual.personident!!.verdi)
        assertEquals(pdlFolkeregisteridentifikator.type, actual.personident!!.type)
        assertTlfnr(pdlPerson.telefonnummer, actual.tlfnr!!)
        assertUtenlandskBank(konto, actual.utenlandskbank!!, kodeverk)
        assertEquals(kodeverk.statsborgerskaptermer, actual.statsborgerskap)
        assertEquals("${kodeverk.foedekommuneterm}, ${kodeverk.foedelandterm}", actual.foedested)
        assertEquals(pdlSivilstand, actual.sivilstand)
        assertEquals(pdlKjoenn, actual.kjoenn)
    }

    private fun assertTlfnr(expected: List<PdlTelefonnummer>, actual: Tlfnr) {
        assertEquals(expected.find { it.prioritet == 1 }?.nummer, actual.telefonHoved!!)
        assertEquals(expected.find { it.prioritet == 2 }?.nummer, actual.telefonAlternativ!!)
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