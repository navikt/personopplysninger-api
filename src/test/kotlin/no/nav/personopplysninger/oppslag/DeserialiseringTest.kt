package no.nav.personopplysninger.oppslag

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import no.nav.personopplysninger.config.RestClientConfiguration
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.consumerutils.unmarshalList
import no.nav.personopplysninger.features.auth.Navn
import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.institusjon.domain.Institusjonstype
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.features.personalia.dto.getJson
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.testutils.TestDataClass
import no.nav.personopplysninger.testutils.*
import org.junit.jupiter.api.*
import java.io.InputStreamReader
import javax.ws.rs.ProcessingException
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeserialiseringTest {

    @BeforeAll
    fun setUpMockServer() {
        val kodeverkjson = InputStreamReader(this.javaClass.getResourceAsStream("/json/kodeverk-kjonnstyper.json")).readText()
        val norg2EnhetJson = InputStreamReader(this.javaClass.getResourceAsStream("/json/norg2-enhet.json")).readText()
        val instListJson = InputStreamReader(this.javaClass.getResourceAsStream("/json/inst2.json")).readText()

        mockServer.start()
        configureFor(mockServer.port())
        stubFor(any(urlPathEqualTo("/kodeverk")).willReturn(okJson(kodeverkjson)))
        stubFor(any(urlPathEqualTo("/norg2-enhet")).willReturn(okJson(norg2EnhetJson)))
        stubFor(any(urlPathEqualTo("/testklasse")).willReturn(okJson(testklasseJson())))
        stubFor(any(urlPathEqualTo("/tpsnavn")).willReturn(okJson(tpsNavnJson())))
        stubFor(any(urlPathEqualTo("/inst")).willReturn(okJson(instJson())))
        stubFor(any(urlPathEqualTo("/instlist")).willReturn(okJson(instListJson)))
        stubFor(any(urlPathEqualTo("/medl")).willReturn(okJson(medlJson())))
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

        val testDataClass = response.unmarshalBody<TestDataClass>()
        assertEquals("foo bar", testDataClass.tekst)
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

    @Test
    fun deserialiserInstitusjon() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/inst").request().get()
        val inst = response.unmarshalBody<InnsynInstitusjonsopphold>()
        assertEquals(Institusjonstype.FO, inst.institusjonstype)
        val json = getJson(inst)
        assertTrue(json.contains("Fengsel"))

        val responseList: Response = client.target("http://localhost:8080").path("/instlist").request().get()
        val instList = responseList.unmarshalList<InnsynInstitusjonsopphold>()
        assertEquals(7, instList.size)
    }

    @Test
    fun deserializingMedl() {
        val client = ClientBuilder.newBuilder()
                .register(RestClientConfiguration().clientObjectMapperResolver())
                .build()
        val response: Response = client.target("http://localhost:8080").path("/medl").request().get()
        val medl = response.unmarshalBody<Medlemskapsunntak>()
        assertEquals("Feilregistrert", medl.statusaarsak)
        assertEquals("srvgosys", medl.sporingsinformasjon?.kilde)
    }

    companion object {
        val mockServer: WireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8080))
    }
}
