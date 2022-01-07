package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import no.nav.personopplysninger.config.RestClientConfiguration
import no.nav.personopplysninger.consumerutils.unmarshalList
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.personalia.dto.getJson
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.testutils.endringJson
import no.nav.personopplysninger.testutils.utenlandskKontonummerJson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStreamReader
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerializerTest {

    @Test
    fun testSerializationTelefonnummer() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/__files/endring-telefonnummer.json")).readText()
        val endring = readValue(json, EndringTelefon::class.java)
        assertEquals("KORRIGER", endring.endringstype)
        assertEquals("BRUKER SELV", endring.innmeldtEndring?.kilde)
        assertEquals(3, endring.status.substatus.size)
    }

    @Test
    fun testSerializationKontonummer() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/__files/endring-kontonummer.json")).readText()

        val mockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8080))
        try {
            mockServer.start()
            WireMock.configureFor(mockServer.port())
            WireMock.stubFor(WireMock.any(WireMock.urlPathEqualTo("/kodeverk")).willReturn(WireMock.okJson(json)))

            val client = ClientBuilder.newBuilder()
                    .register(RestClientConfiguration().clientObjectMapperResolver())
                    .build()
            val response: Response = client.target("http://localhost:8080").path("/kodeverk").request().get()
            val endringer: List<EndringKontonummer> = response.unmarshalList()
            val endring = endringer[0]
            assertEquals("OPPRETT", endring.endringstype)
            assertEquals("BRUKER SELV", endring.innmeldtEndring!!.kilde)
            assertEquals(3, endring.status.substatus.size)
        } finally {
            mockServer.stop()
        }
    }

    @Test
    fun testSerializationInstitusjonsopphold() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/__files/inst2.json")).readText()
        val institusjonsopphold = readValue(json, ArrayList<InnsynInstitusjonsopphold>()::class.java)
        assertEquals(7, institusjonsopphold.size)
    }

    @Test
    fun testSerializationKontonummer2() {
        val endring = readValue(endringJson(), EndringKontonummer::class.java)
        assertEquals("12345678910", endring.ident)
        assertEquals(2113, endring.status.endringId)
    }

    @Test
    fun testSerializationUtenlandskKontonummer() {
        val utenlandskKontonummer = readValue(utenlandskKontonummerJson(), Kontonummer::class.java)
        assertEquals("SWE", utenlandskKontonummer.utenlandskKontoInformasjon!!.landkode)
    }

    @Test
    fun testSerializationRetningsnummer() {
        assertTrue { ObjectMapper().canSerialize(RetningsnummerDTO::class.java) }
    }

    @Test
    fun testRetningsnummerMapping() {
        val json: String = InputStreamReader(this.javaClass.getResourceAsStream("/__files/retningsnumre.json")).readText()
        val response: GetKodeverkKoderBetydningerResponse = readValue(json, GetKodeverkKoderBetydningerResponse::class.java)
        assertEquals(2, response.betydninger.entries.size)
        assertEquals("+51", response.betydninger.entries.first().key)
        assertEquals("Peru", response.betydninger.entries.first().value.first().beskrivelser.entries.first().value.tekst)

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
        val json = InputStreamReader(this.javaClass.getResourceAsStream("/__files/validation-error.json")).readText()
        val validationError = readValue(json, Error::class.java)
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
        val json = InputStreamReader(this.javaClass.getResourceAsStream("/__files/kodeverk-kjonnstyper.json")).readText()
        val kodeverk = readValue(json, GetKodeverkKoderBetydningerResponse::class.java)
        assertEquals("Kvinne", kodeverk.betydninger.getValue("K")[0].beskrivelser.getValue("nb").term)
    }

    private fun <T> readValue(json: String, t: Class<T>): T {
        return RestClientConfiguration().clientObjectMapperResolver().getContext(t).readValue(json, t)
    }
}
