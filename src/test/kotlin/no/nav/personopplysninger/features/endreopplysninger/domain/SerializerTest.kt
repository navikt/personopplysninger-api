package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
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
                "    \"substatus\": [\n" +
                "      {\n" +
                "        \"status\": \"IGNORED\",\n" +
                "        \"kode\": null,\n" +
                "        \"beskrivelse\": \"Er ikke gyldig Dnummer\",\n" +
                "        \"referanse\": \"2fa7b57c-8a3b-4f40-b999-67fd848021f8\",\n" +
                "        \"domene\": \"FREG\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"status\": \"IGNORED\",\n" +
                "        \"kode\": null,\n" +
                "        \"beskrivelse\": null,\n" +
                "        \"referanse\": \"e23ef345-cb94-466b-a5db-bda7835adfa3\",\n" +
                "        \"domene\": \"PDL\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"status\": \"ERROR\",\n" +
                "        \"kode\": \"VALIDERINGSFEIL\",\n" +
                "        \"beskrivelse\": \"Person ikke funnet i TPS\",\n" +
                "        \"referanse\": \"b8a2619b-1e9e-4d67-9d16-3ec678791685\",\n" +
                "        \"domene\": \"TPS\"\n" +
                "      }\n" +
                "    ]" +
                "  }\n" +
                "}"
        val endring = ObjectMapper().readValue(json, EndringTelefon::class.java)
        assertEquals("KORRIGER", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring.kilde)
        assertEquals(3, endring.status.substatus.size)
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
        assertEquals("+52", retningsnumre[0].landskode)
        assertEquals("Mexico", retningsnumre.get(0).land)
        assertEquals("Peru", retningsnumre.get(1).land)
    }

    @Test
    fun testSerializeValidationError() {
        val json = "{\n" +
                "  \"message\": \"Kontonummer feilet validering\",\n" +
                "  \"details\": [\n" +
                "    {\n" +
                "      \"name\": \"kontonummer.utenlandskKontoInformasjon\",\n" +
                "      \"message\": \"Swift (BIC) eller bankinformasjon må være populert\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val validationError = ObjectMapper().readValue(json, ValidationError::class.java)
        assertEquals("Kontonummer feilet validering", validationError.message)
        assertEquals("kontonummer.utenlandskKontoInformasjon", validationError.details.first().name)
    }
}