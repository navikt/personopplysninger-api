package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.institusjon.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.kodeverk.api.RetningsnummerDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStreamReader
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    @Test
    fun testSerializationTelefonnummer() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/json/endring-telefonnummer.json")).readText()
        val endring = ObjectMapper().readValue(json, EndringTelefon::class.java)
        assertEquals("KORRIGER", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring.kilde)
        assertEquals(3, endring.status.substatus.size)
    }

    @Test
    fun testSerializationInstitusjonsopphold() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/json/inst2.json")).readText()
        val institusjonsopphold = ObjectMapper().readValue(json, ArrayList<InnsynInstitusjonsopphold>()::class.java)
        assertEquals(7, institusjonsopphold.size)
    }

    @Test
    fun testSerializationKontonummer() {
        val json: String = "{" +
                "\"endringstype\":\"OPPRETT\"," +
                "\"ident\":\"12345678910\"," +
                "\"lineage\":\"cdbd4444-f851-4adb-b22f-f8794825bb22\"," +
                "\"opplysningsId\":null," +
                "\"status\":{\"endringId\":2113,\"statusType\":\"DONE\"}," +
                "\"innmeldtEndring\":{\"kilde\":\"BRUKER SELV\",\"utenlandskKontoInformasjon\":null," +
                "\"value\":\"11112233333\"}}"
        val endring = ObjectMapper().readValue(json, EndringKontonummer::class.java)
        assertEquals("12345678910", endring.ident)
        assertEquals(2113, endring.status.endringId)
    }

    @Test
    fun testSerializationUtenlandskKontonummer() {
        val json: String = "{ \"utenlandskKontoInformasjon\": {\"landkode\": \"SWE\", \"valuta\": \"EURO\", \"SWIFT\": \"1234\"},  \"value\": \"11112233333\"}"
        val utenlandskKontonummer = ObjectMapper().readValue(json, Kontonummer::class.java)
    }

    @Test
    fun testSerializationRetningsnummer() {
        assertTrue { ObjectMapper().canSerialize(RetningsnummerDTO::class.java) }
    }

    @Test
    fun testRetningsnummerMapping() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/json/retningsnumre.json")).readText()
        val response: GetKodeverkKoderBetydningerResponse = ObjectMapper().readValue(json, GetKodeverkKoderBetydningerResponse::class.java)
        assertEquals(2, response.betydninger.entries.size)
        assertEquals("+51", response.betydninger.entries.first().key)
        assertEquals("Peru", response.betydninger.entries.first().value.first().beskrivelser.entries.first().value.tekst)

        val retningsnumre: Array<RetningsnummerDTO> = response.betydninger
                .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
                .sortedBy { it.land }
                .toTypedArray()

        assertEquals(2, retningsnumre.size)
        assertEquals("+52", retningsnumre[0].landskode)
        assertEquals("Mexico", retningsnumre.get(0).land)
        assertEquals("Peru", retningsnumre.get(1).land)
    }

    @Test
    fun testSerializeValidationError() {
        val json = InputStreamReader(this.javaClass.getResourceAsStream("/json/validation-error.json")).readText()
        val validationError = ObjectMapper().readValue(json, Error::class.java)
        assertEquals("Validering feilet", validationError.message)
        assertEquals(3, validationError.details.size)
        val feilForFelt = validationError.details.get("objekt.feltnavn")
        assertEquals(3, feilForFelt!!.size)
        assertEquals("valideringsfeil 1", feilForFelt!![0])
    }
}
