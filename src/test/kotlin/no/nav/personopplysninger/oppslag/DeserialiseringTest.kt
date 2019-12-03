package no.nav.personopplysninger.oppslag

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import no.nav.personopplysninger.config.RestClientConfiguration
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.features.auth.Navn
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import org.glassfish.jersey.client.ClientConfig
import org.junit.jupiter.api.*
import java.io.InputStreamReader
import javax.ws.rs.ProcessingException
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeserialiseringTest {

    @BeforeAll
    fun setUpMockServer() {
        val kodeverkjson = InputStreamReader(this.javaClass.getResourceAsStream("/json/kodeverk-kjonnstyper.json")).readText()
        val norg2EnhetJson = InputStreamReader(this.javaClass.getResourceAsStream("/json/norg2-enhet.json")).readText()
        val testklasseJson = """
            {
                "dato": "1900-01-01",
                "tekst": "foo bar"
            }
        """.trimIndent()
        val tpsNavn = """
            {
                "kortNavn": "AMIZIC VINAYAGUM-MASK",
                "fornavn": "VINAYAGUM-MASK",
                "etternavn": "AMIZIC"
            }
        """.trimIndent()
        mockServer.start()
        configureFor(mockServer.port())
        stubFor(any(urlPathEqualTo("/kodeverk")).willReturn(okJson(kodeverkjson)))
        stubFor(any(urlPathEqualTo("/norg2-enhet")).willReturn(okJson(norg2EnhetJson)))
        stubFor(any(urlPathEqualTo("/testklasse")).willReturn(okJson(testklasseJson)))
        stubFor(any(urlPathEqualTo("/tpsnavn")).willReturn(okJson(tpsNavn)))
    }

    @AfterAll
    fun shutDown() {
        mockServer.stop()
    }

    @Test
    fun deserialiseringFungererDersomDataClassHarJsonAnnotering() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/kodeverk").request().get()
        response.unmarshalBody<GetKodeverkKoderBetydningerResponse>()
    }

    @Test
    fun deserialiseringFeilerMedClientDersomDataClassManglerJsonAnnotering_WorkaroundFungerer() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/testklasse").request().get()
        response.bufferEntity()
        assertThrows<ProcessingException> {
            response.readEntity(TestDataClass::class.java)
        }

        val testDataClass = RestClientConfiguration()
                .clientObjectMapperResolver()
                .getContext(TestDataClass::class.java)
                .readValue(
                        response.readEntity(String::class.java),
                        TestDataClass::class.java)
        assertEquals("foo bar", testDataClass.tekst)
    }

    @Test
    fun alternativtTestOppsettAvClientFeilerOgsaaDersomDataClassManglerJsonAnnotering() {
        val objectMapper = ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

        val provider = JacksonJaxbJsonProvider()
                .setMapper(objectMapper)

        val clientConfig = ClientConfig()
                .register(provider)
                .register(KotlinModule())
                .register(JavaTimeModule())

        val client = ClientBuilder.newBuilder()
                .withConfig(clientConfig)
                .register(KotlinModule())
                .register(JavaTimeModule())
                .build()

        val response: Response = client.target("http://localhost:8080").path("/testklasse").request().get()
        assertThrows<ProcessingException> {
            response.readEntity(TestDataClass::class.java)
        }
    }

    @Test
    fun deserialiseringNorg2() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/norg2-enhet").request().get()
        val norg2Enhet: Norg2Enhet = response.unmarshalBody()
        assertEquals("NAV Aremark", norg2Enhet.navn)
        assertEquals("0118", norg2Enhet.enhetNr)
        assertEquals("287", norg2Enhet.antallRessurser)
    }

    @Test
    fun deserialiserNavn() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/tpsnavn").request().get()
        val navn: Navn = response.unmarshalBody()
        assertEquals("VINAYAGUM-MASK", navn.fornavn)
    }

    companion object {
        val mockServer: WireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8080))
    }
}
