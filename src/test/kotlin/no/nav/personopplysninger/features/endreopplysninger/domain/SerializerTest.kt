package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
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
}