package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.personalia.dto.getJson
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import no.nav.personopplysninger.testutils.endringJson
import no.nav.personopplysninger.testutils.utenlandskKontonummerJson
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    @Test
    fun testSerializationTelefonnummer() {
        val json: String = readFile("endring-telefonnummer.json")
        val endringList: List<EndringTelefon> = objectMapper.readValue(json);
        val endring = endringList[0]
        assertEquals("KORRIGER", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring?.kilde)
        assertEquals(3, endring.status.substatus.size)
    }

    @Test
    fun testSerializationKontonummer() {
        val json: String = readFile("endring-kontonummer.json")
        val endringer: List<EndringKontonummer> = objectMapper.readValue(json)
        val endring = endringer[0]
        assertEquals("OPPRETT", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring!!.kilde)
        assertEquals(3, endring.status.substatus.size)
    }

    @Test
    fun testSerializationInstitusjonsopphold() {
        val json: String = readFile("inst2.json")
        val institusjonsopphold: List<InnsynInstitusjonsopphold> = objectMapper.readValue(json)
        assertEquals(7, institusjonsopphold.size)
    }

    @Test
    fun testSerializationKontonummer2() {
        val endring: EndringKontonummer = objectMapper.readValue(endringJson())
        assertEquals("12345678910", endring.ident)
        assertEquals(2113, endring.status.endringId)
    }

    @Test
    fun testSerializationUtenlandskKontonummer() {
        val utenlandskKontonummer: Kontonummer = objectMapper.readValue(utenlandskKontonummerJson())
        assertEquals("SWE", utenlandskKontonummer.utenlandskKontoInformasjon!!.landkode)
    }

    @Test
    fun testSerializationRetningsnummer() {
        assertTrue { objectMapper.canSerialize(RetningsnummerDTO::class.java) }
    }

    @Test
    fun testRetningsnummerMapping() {
        val json: String = readFile("retningsnumre.json")
        val response: GetKodeverkKoderBetydningerResponse = objectMapper.readValue(json)
        assertEquals(2, response.betydninger.entries.size)
        assertEquals("+51", response.betydninger.entries.first().key)
        assertEquals(
            "Peru",
            response.betydninger.entries.first().value.first().beskrivelser.entries.first().value.tekst
        )

        val retningsnumre: Array<RetningsnummerDTO> = response.betydninger
            .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
            .sortedBy { it.land }
            .toTypedArray()

        assertEquals(2, retningsnumre.size)
        assertEquals("+52", retningsnumre[0].landskode)
        assertEquals("Mexico", retningsnumre[0].land)
        assertEquals("Peru", retningsnumre[1].land)
    }

    @Test
    fun testSerializeValidationError() {
        val json: String = readFile("validation-error.json")
        val validationError: Error = objectMapper.readValue(json)
        assertEquals("Validering feilet", validationError.message)
        assertEquals(3, validationError.details!!.size)
        val feilForFelt = validationError.details!!["objekt.feltnavn"]
        assertEquals(3, feilForFelt!!.size)
        assertEquals("valideringsfeil 1", feilForFelt[0])
    }

    @Test
    fun testSubType() {
        val telefonnummer = Telefonnummer("+47", "11223344", 1)
        val json = getJson(telefonnummer)
        assertTrue(json.contains("@type"))
    }

    @Test
    fun testKodeverk() {
        val json: String = readFile("kodeverk-kjonnstyper.json")
        val kodeverk: GetKodeverkKoderBetydningerResponse = objectMapper.readValue(json)
        assertEquals("Kvinne", kodeverk.betydninger.getValue("K")[0].beskrivelser.getValue("nb").term)
    }
}
