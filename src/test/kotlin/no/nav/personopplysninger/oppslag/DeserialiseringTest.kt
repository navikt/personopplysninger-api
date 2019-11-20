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
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
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
        val testklasseJson = """
                  {
        "dato": "1900-01-01",
        "tekst": "foo bar"
      }
        """.trimIndent()
        mockServer.start()
        configureFor(mockServer.port())
        stubFor(any(urlPathEqualTo("/kodeverk")).willReturn(okJson(kodeverkjson)))
        stubFor(any(urlPathEqualTo("/testklasse")).willReturn(okJson(testklasseJson)))
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
        response.readEntity(GetKodeverkKoderBetydningerResponse::class.java)
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

        val testDataClass = ConsumerFactory.readEntityAsString(TestDataClass::class.java, response)
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

    companion object {
        val mockServer: WireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8080))
    }


}
