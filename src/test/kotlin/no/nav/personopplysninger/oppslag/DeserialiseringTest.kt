package no.nav.personopplysninger.oppslag

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import no.nav.personopplysninger.config.RestClientConfiguration
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.consumerutils.unmarshalList
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.EndreKontaktadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.Postboksadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.institusjon.domain.Institusjonstype
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.features.personalia.dto.getJson
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlResponse
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PDLErrorType
import no.nav.personopplysninger.features.personalia.pdl.dto.error.PdlErrorResponse
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import no.nav.personopplysninger.testutils.TestDataClass
import no.nav.personopplysninger.testutils.instJson
import no.nav.personopplysninger.testutils.testklasseJson
import no.nav.personopplysninger.testutils.tpsNavnJson
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
        val medlListJson = InputStreamReader(this.javaClass.getResourceAsStream("/json/medl-medlemskapsunntak.json")).readText()

        mockServer.start()
        configureFor(mockServer.port())
        stubFor(any(urlPathEqualTo("/kodeverk")).willReturn(okJson(kodeverkjson)))
        stubFor(any(urlPathEqualTo("/norg2-enhet")).willReturn(okJson(norg2EnhetJson)))
        stubFor(any(urlPathEqualTo("/testklasse")).willReturn(okJson(testklasseJson())))
        stubFor(any(urlPathEqualTo("/tpsnavn")).willReturn(okJson(tpsNavnJson())))
        stubFor(any(urlPathEqualTo("/inst")).willReturn(okJson(instJson())))
        stubFor(any(urlPathEqualTo("/instlist")).willReturn(okJson(instListJson)))
        stubFor(any(urlPathEqualTo("/medl")).willReturn(okJson(medlListJson)))
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
    fun canDeserializePdlResponse() {
        val json = """
            {
              "data": {
                "person": {
                  "telefonnummer": [
                    {
                      "landskode": "+47",
                      "nummer": "22334455",
                      "prioritet": 1,
                      "metadata": {
                        "opplysningsId": "b2cf4a5c-99e9-46e5-88d9-65d79aee3bb0"
                      }
                    }
                  ],
                  "kontaktadresse": [
                    {
                      "gyldigFraOgMed": "2020-03-24T00:00",
                      "gyldigTilOgMed": null,
                      "type": "Innland",
                      "coAdressenavn": null,
                      "postboksadresse": null,
                      "vegadresse": null,
                      "postadresseIFrittFormat": {
                        "adresselinje1": "Linjeveien 1",
                        "adresselinje2": "1234 LINJE",
                        "adresselinje3": "Norge",
                        "postnummer": null
                      },
                      "utenlandskAdresse": null,
                      "utenlandskAdresseIFrittFormat": null,
                      "folkeregistermetadata": {
                        "ajourholdstidspunkt": null,
                        "gyldighetstidspunkt": "2020-03-24T00:00",
                        "opphoerstidspunkt": null,
                        "kilde": "KILDE_DSF",
                        "aarsak": null,
                        "sekvens": null
                      },
                      "metadata": {
                        "opplysningsId": "abcd1234-1234-abcd-1234-123456abcdef",
                        "master": "Freg",
                        "endringer": [
                          {
                            "type": "OPPRETT",
                            "registrert": "2020-04-24T13:07:20",
                            "registrertAv": "Folkeregisteret",
                            "systemkilde": "FREG",
                            "kilde": "KILDE_DSF"
                          }
                        ],
                        "historisk": false
                      }
                    }
                  ]
                }
              }
            }
        """.trimIndent()

        val person: PdlResponse = RestClientConfiguration.applicationObjectMapper.readValue(json)
        val telefonnummer = person.data.person.telefonnummer.first()
        val kontaktadresse = person.data.person.kontaktadresse.first()

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)

        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje1, "Linjeveien 1")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje2, "1234 LINJE")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje3, "Norge")
    }

    @Test
    fun canDeserializePdlErrorResponse() {
        val json = """
            {
              "errors": [
                {
                  "message": "Ikke autentisert",
                  "locations": [
                    {
                      "line": 1,
                      "column": 23
                    }
                  ],
                  "path": [
                    "person"
                  ],
                  "extensions": {
                    "code": "unauthenticated",
                    "classification": "ExecutionAborted"
                  }
                }
              ],
              "data": {
                "person": null
              }
            }
        """.trimIndent()

        val pdlErrorResponse: PdlErrorResponse = jacksonObjectMapper().readValue(json)
        val errorType: PDLErrorType = pdlErrorResponse.errors.first().errorType
        assertEquals(errorType, PDLErrorType.NOT_AUTHENTICATED)
    }

    @Test
    fun canDeserializeLegacyTelefonnummerFormat() {
        val json = """
            {
              "landskode": "+47",
              "nummer": "22334455",
              "type": "MOBIL"
            }
        """.trimIndent()

        val telefonnummer: Telefonnummer = jacksonObjectMapper().readValue(json)

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)
    }

    @Test
    fun canDeserializePMKontaktadresseResponse() {
        val json = """
        {
          "ident": "12045678900",
          "endringstype": "OPPRETT",
          "opplysningstype": "KONTAKTADRESSE",
          "endringsmelding": {
            "@type": "KONTAKTADRESSE",
            "gyldigFraOgMed": "2020-01-01",
            "gyldigTilOgMed": "2020-07-01",
            "coAdressenavn": "Grå Banan",
            "kilde": "test",
            "adresse": {
              "@type": "POSTBOKSADRESSE",
              "postbokseier": "Snill Tester",
              "postboks": "Postboks 13",
              "postnummer": "0001"
            }
          }
        }
        """.trimIndent()

        val response: EndreKontaktadresse = RestClientConfiguration.applicationObjectMapper.readValue(json)

        assertEquals(response.ident, "12045678900")
        assertEquals((response.endringsmelding.adresse as Postboksadresse).postbokseier, "Snill Tester")
    }

    @Test
    fun canDeserializeTelefonnummerFormat() {
        val json = """
            {
              "landskode": "+47",
              "nummer": "22334455",
              "prioritet": 1
            }
        """.trimIndent()

        val telefonnummer: Telefonnummer = jacksonObjectMapper().readValue(json)

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)
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

    companion object {
        val mockServer: WireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8080))
    }
}
