package no.nav.personopplysninger.serialisering

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import no.nav.personopplysninger.common.pdl.dto.PdlResponse
import no.nav.personopplysninger.config.jsonConfig
import no.nav.personopplysninger.institusjon.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.institusjon.dto.Institusjonstype
import no.nav.personopplysninger.testutils.TestFileReader.readFile
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeserialiseringTest {

    val serializer = jsonConfig()

    @Test
    fun canDeserializePdlResponse() {
        val json = readFile("pdl.json")

        val person: PdlResponse = serializer.decodeFromString(json)
        val telefonnummer = person.data.person!!.telefonnummer.first()
        val kontaktadresse = person.data.person!!.kontaktadresse.first()

        assertEquals(telefonnummer.landskode, "+47")
        assertEquals(telefonnummer.nummer, "55553334")
        assertEquals(telefonnummer.prioritet, 1)

        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje1, "Hylkjelia")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje2, "5109 HYLKJE")
        assertEquals(kontaktadresse.postadresseIFrittFormat?.adresselinje3, "Norge")
    }

    @Test
    fun deserialiserInstitusjon() {
        val response = readFile("inst2.json")
        val instList: List<InnsynInstitusjonsopphold> = serializer.decodeFromString(response)
        assertEquals(7, instList.size)

        val inst = instList[0]
        assertEquals(Institusjonstype.FO, inst.institusjonstype)
        val json = serializer.encodeToString(inst)
        assertTrue(json.contains("Fengsel"))
    }
}
