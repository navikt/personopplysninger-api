package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    @Test
    fun testSerialization() {
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
}