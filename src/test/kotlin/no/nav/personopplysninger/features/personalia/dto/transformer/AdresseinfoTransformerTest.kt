package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlData
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlDataWithoutAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonaliaKodeverk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = createDummyPdlData()
        val kodeverk = createDummyPersonaliaKodeverk()
        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk)

        assertNotNull(actual.geografiskTilknytning)
        assertNotNull(actual.deltBosted)
        assertNotNull(actual.oppholdsadresser)
        assertNotNull(actual.kontaktadresser)
        assertEquals(1, actual.kontaktadresser.size)
        assertEquals(1, actual.oppholdsadresser.size)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = createDummyPdlDataWithoutAdresser()
        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertTrue(actual.kontaktadresser.isEmpty())
        assertNull(actual.geografiskTilknytning)
        assertNull(actual.deltBosted)
        assertTrue(actual.oppholdsadresser.isEmpty())
    }

}
