package no.nav.personopplysninger.serialisering

import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.common.consumer.kodeverk.dto.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.common.consumer.kodeverk.dto.Retningsnummer
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import no.nav.personopplysninger.endreopplysninger.dto.outbound.Error
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerialiseringTest {

    private val serializer = jsonConfig()

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

        val retningsnumre: List<Retningsnummer> = response.betydninger
            .map { entry ->
                Retningsnummer(
                    entry.key,
                    entry.value.first().beskrivelser.entries.first().value.tekst
                )
            }
            .sortedBy { it.land }

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
        assertEquals(3, validationError.details?.size)
        val feilForFelt = validationError.details?.get("objekt.feltnavn")
        assertEquals(3, feilForFelt?.size)
        assertEquals("valideringsfeil 1", feilForFelt?.get(0))
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
