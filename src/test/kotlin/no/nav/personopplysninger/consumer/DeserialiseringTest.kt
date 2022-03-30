package no.nav.personopplysninger.consumer

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.inst.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.consumer.inst.domain.Institusjonstype
import no.nav.personopplysninger.consumer.kodeverk.domain.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.consumer.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.consumer.pdl.dto.PdlResponse
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.EndreKontaktadresse
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.Postboksadresse
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.Telefonnummer
import no.nav.personopplysninger.testutils.PMKontaktadresseJson
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import no.nav.personopplysninger.testutils.instJson
import no.nav.personopplysninger.testutils.pdlJson
import no.nav.personopplysninger.testutils.telefonnummerJson
import no.nav.personopplysninger.util.getJson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeserialiseringTest {

    @Test
    fun deserialiseringFungererDersomDataClassHarJsonAnnotering() {
        val response = readFile("kodeverk-kjonnstyper.json")
        objectMapper.readValue(response, GetKodeverkKoderBetydningerResponse::class.java)
    }

    @Test
    fun canDeserializePdlResponse() {
        val json = pdlJson()

        val person: PdlResponse = objectMapper.readValue(json)
        val telefonnummer = person.data.person!!.telefonnummer.first()
        val kontaktadresse = person.data.person!!.kontaktadresse.first()

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)

        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje1, "Linjeveien 1")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje2, "1234 LINJE")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje3, "Norge")
    }

    @Test
    fun canDeserializeLegacyTelefonnummerFormat() {
        val json = telefonnummerJson()

        val telefonnummer: Telefonnummer = objectMapper.readValue(json)

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)
    }

    @Test
    fun canDeserializePMKontaktadresseResponse() {
        val json = PMKontaktadresseJson()

        val response: EndreKontaktadresse = objectMapper.readValue(json)

        assertEquals(response.ident, "12045678900")
        assertEquals((response.endringsmelding.adresse as Postboksadresse).postbokseier, "Snill Tester")
    }

    @Test
    fun deserialiseringNorg2() {
        val response = readFile("norg2-enhet.json")
        val norg2Enhet: Norg2Enhet = objectMapper.readValue(response)
        assertEquals("NAV Aremark", norg2Enhet.navn)
        assertEquals("0118", norg2Enhet.enhetNr)
        assertEquals("287", norg2Enhet.antallRessurser)
    }

    @Test
    fun deserialiserInstitusjon() {
        val response = instJson()
        val inst: InnsynInstitusjonsopphold = objectMapper.readValue(response)
        assertEquals(Institusjonstype.FO, inst.institusjonstype)
        val json = getJson(inst)
        assertTrue(json.contains("Fengsel"))

        val responseList = readFile("inst2.json")

        val instList: List<InnsynInstitusjonsopphold> = objectMapper.readValue(responseList)
        assertEquals(7, instList.size)
    }
}
