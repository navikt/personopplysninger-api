package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlData
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlDataWithoutAdresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = createDummyPdlData()

        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertEquals(1, actual.kontaktadresse.size)
        assertNotNull(actual.geografiskTilknytning)
        assertNotNull(actual.deltBosted)
        assertNotNull(actual.oppholdsadresse)
        assertNotNull(actual.kontaktadresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = createDummyPdlDataWithoutAdresser()

        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertTrue(actual.kontaktadresse.isEmpty())
        assertNull(actual.geografiskTilknytning)
        assertNull(actual.deltBosted)
        assertNull(actual.oppholdsadresse)
    }

}
