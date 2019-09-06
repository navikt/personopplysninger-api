package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.kodeverk.api.RetningsnummerDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    @Test
    fun testSerializationTelefonnummer() {
        val json: String = "{\n" +
                "  \"endringstype\": \"KORRIGER\",\n" +
                "  \"ident\": 12345678910,\n" +
                "  \"innmeldtEndring\": \"{\\\"landskode\\\":\\\"+47\\\",\\\"nummer\\\":\\\"123123133\\\",\\\"type\\\":\\\"HJEM\\\",\\\"kilde\\\":\\\"BRUKER SELV\\\"}\"," +
                "  \"lineage\": \"12ab7a9c-6e5c-44da-12d3-0152ba44123\",\n" +
                "  \"opplysningsId\": \"85dc7a9c-7e5c-41da-87c9-0320fb74165\",\n" +
                "  \"opplysningstype\": \"UTENLANDSKIDENTIFIKASJONSNUMMER\",\n" +
                "  \"opprettet\": \"2019-01-01 10:32\",\n" +
                "  \"sendt\": false,\n" +
                "  \"status\": {\n" +
                "    \"endringId\": 65,\n" +
                "    \"statusType\": \"PENDING\",\n" +
                "    \"substatus\": \"Se modell for SubStatus for eksempel.\"\n" +
                "  }\n" +
                "}"
        val endring = ObjectMapper().readValue(json, EndringTelefon::class.java)
        assertEquals("KORRIGER", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring.kilde)
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
    fun testSerializationRetningsnummer() {
        assertTrue { ObjectMapper().canSerialize(RetningsnummerDTO::class.java) }
    }

    @Test
    fun testRetningsnummerMapping() {
        val json: String = "{\n" +
                "\"betydninger\":\n" +
                "\t{\n" +
                "\t\"+51\": [\n" +
                "\t\t{\n" +
                "\t\t\"gyldigFra\":\"1900-01-01\",\n" +
                "\t\t\"gyldigTil\":\"9999-12-31\",\n" +
                "\t\t\"beskrivelser\":\n" +
                "\t\t\t{\n" +
                "\t\t\t\"nb\":\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\"term\":\"Peru\",\n" +
                "\t\t\t\t\"tekst\":\"Peru\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"+52\":[\n" +
                "\t\t{\n" +
                "\t\t\"gyldigFra\":\"1900-01-01\",\n" +
                "\t\t\"gyldigTil\":\"9999-12-31\",\n" +
                "\t\t\"beskrivelser\":\n" +
                "\t\t\t{\n" +
                "\t\t\t\"nb\":\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\"term\":\"Mexico\",\n" +
                "\t\t\t\t\"tekst\":\"Mexico\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "\t}\n" +
                "}"
        val response: GetKodeverkKoderBetydningerResponse = ObjectMapper().readValue(json, GetKodeverkKoderBetydningerResponse::class.java)
        assertEquals(2, response.betydninger.entries.size)
        assertEquals("+51", response.betydninger.entries.first().key)
        assertEquals("Peru", response.betydninger.entries.first().value.first().beskrivelser.entries.first().value.tekst)

        val retningsnumre: Array<RetningsnummerDTO> = response.betydninger
                .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
                .sortedBy { it.land }
                .toTypedArray()

        assertEquals(2, retningsnumre.size)
        assertEquals("+52", retningsnumre[0].retningsnummer)
        assertEquals("Mexico", retningsnumre.get(0).land)
        assertEquals("Peru", retningsnumre.get(1).land)
    }

    @Test
    fun testSerialize() {
        val vals = arrayOf("Sverige", "Angola")
        assertTrue { ObjectMapper().canSerialize(Array<String>::class.java) }
        assertEquals("[\"Sverige\",\"Angola\"]", ObjectMapper().writeValueAsString(vals))
    }
}