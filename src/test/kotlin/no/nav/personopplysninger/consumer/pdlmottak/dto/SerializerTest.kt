package no.nav.personopplysninger.consumer.pdlmottak.dto

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.consumer.inst.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.consumer.kodeverk.dto.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.consumer.pdlmottak.dto.outbound.Endring
import no.nav.personopplysninger.consumer.pdlmottak.dto.outbound.Error
import no.nav.personopplysninger.features.endreopplysninger.dto.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.dto.Retningsnummer
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import no.nav.personopplysninger.testutils.utenlandskKontonummerJson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    val serializer = jsonConfig()

    @Test
    fun testSerializationTelefonnummer() {
        val json: String = readFile("endring-telefonnummer.json")
        val endringList: List<Endring> = serializer.decodeFromString(json)
        val endring = endringList[0]
        assertEquals(3, endring.status.substatus.size)
    }

    @Test
    fun testSerializationInstitusjonsopphold() {
        val json: String = readFile("inst2.json")
        val institusjonsopphold: List<InnsynInstitusjonsopphold> = serializer.decodeFromString(json)
        assertEquals(7, institusjonsopphold.size)
    }

    @Test
    fun testSerializationUtenlandskKontonummer() {
        val utenlandskKontonummer: Kontonummer = serializer.decodeFromString(utenlandskKontonummerJson())
        assertEquals("SWE", utenlandskKontonummer.utenlandskKontoInformasjon!!.landkode)
    }

    @Test
    fun testRetningsnummerMapping() {
        val json: String = readFile("retningsnumre.json")
        val response: GetKodeverkKoderBetydningerResponse = serializer.decodeFromString(json)
        assertEquals(2, response.betydninger.entries.size)
        assertEquals("+51", response.betydninger.entries.first().key)
        assertEquals(
            "Peru",
            response.betydninger.entries.first().value.first().beskrivelser.entries.first().value.tekst
        )

        val retningsnumre: Array<Retningsnummer> = response.betydninger
            .map { entry ->
                Retningsnummer(
                    entry.key,
                    entry.value.first().beskrivelser.entries.first().value.tekst
                )
            }
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
        val validationError: Error = serializer.decodeFromString(json)
        assertEquals("Validering feilet", validationError.message)
        assertEquals(3, validationError.details!!.size)
        val feilForFelt = validationError.details!!["objekt.feltnavn"]
        assertEquals(3, feilForFelt!!.size)
        assertEquals("valideringsfeil 1", feilForFelt[0])
    }

    @Test
    fun testSubType() {
        val telefonnummer = Telefonnummer(landskode = "+47", nummer = "11223344", prioritet = 1)
        val json = serializer.encodeToString(telefonnummer)
        assertTrue(json.contains("@type"))
    }

    @Test
    fun testKodeverk() {
        val json: String = readFile("kodeverk-kjonnstyper.json")
        val kodeverk: GetKodeverkKoderBetydningerResponse = serializer.decodeFromString(json)
        assertEquals("Kvinne", kodeverk.betydninger.getValue("K")[0].beskrivelser.getValue("nb").term)
    }
}