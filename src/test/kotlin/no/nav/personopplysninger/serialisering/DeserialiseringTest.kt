package no.nav.personopplysninger.serialisering

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.common.kodeverk.dto.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.common.pdl.dto.PdlResponse
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import no.nav.personopplysninger.institusjon.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.institusjon.dto.Institusjonstype
import no.nav.personopplysninger.personalia.consumer.dto.Norg2Enhet
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import no.nav.personopplysninger.testutils.instJson
import no.nav.personopplysninger.testutils.pdlJson
import no.nav.personopplysninger.testutils.telefonnummerJson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeserialiseringTest {

    val serializer = jsonConfig()

    @Test
    fun deserialiseringFungererDersomDataClassHarJsonAnnotering() {
        val response = readFile("kodeverk-kjonnstyper.json")
        serializer.decodeFromString<GetKodeverkKoderBetydningerResponse>(response)
    }

    @Test
    fun canDeserializePdlResponse() {
        val json = pdlJson()

        val person: PdlResponse = serializer.decodeFromString(json)
        val telefonnummer = person.data.person!!.telefonnummer.first()
        val kontaktadresse = person.data.person!!.kontaktadresse.first()

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)

        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje1, "Linjeveien 1")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje2, "1234 LINJE")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje3, "Norge")
    }

    @Test
    fun canDeserializeLegacyTelefonnummerFormat() {
        val json = telefonnummerJson()

        val telefonnummer: Telefonnummer = serializer.decodeFromString(json)

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "22334455")
        assertEquals(telefonnummer.prioritet, 1)
    }

    @Test
    fun deserialiseringNorg2() {
        val response = readFile("norg2-enhet.json")
        val norg2Enhet: Norg2Enhet = serializer.decodeFromString(response)
        assertEquals("NAV Aremark", norg2Enhet.navn)
        assertEquals("0118", norg2Enhet.enhetNr)
        assertEquals("287", norg2Enhet.antallRessurser)
    }

    @Test
    fun deserialiserInstitusjon() {
        val response = instJson()
        val inst: InnsynInstitusjonsopphold = serializer.decodeFromString(response)
        assertEquals(Institusjonstype.FO, inst.institusjonstype)
        val json = serializer.encodeToString(inst)
        assertTrue(json.contains("Fengsel"))

        val responseList = readFile("inst2.json")

        val instList: List<InnsynInstitusjonsopphold> = serializer.decodeFromString(responseList)
        assertEquals(7, instList.size)
    }
}
