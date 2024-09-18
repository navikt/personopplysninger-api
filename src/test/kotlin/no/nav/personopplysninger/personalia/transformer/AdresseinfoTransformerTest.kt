package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPdlData
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPdlDataWithoutAdresser
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = defaultPdlData
        val kodeverk = defaultPersonaliaKodeverk
        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk)

        assertNotNull(actual.deltBosted)
        assertNotNull(actual.oppholdsadresser)
        assertNotNull(actual.kontaktadresser)
        assertEquals(1, actual.kontaktadresser.size)
        assertEquals(1, actual.oppholdsadresser.size)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = defaultPdlDataWithoutAdresser
        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertTrue(actual.kontaktadresser.isEmpty())
        assertNull(actual.deltBosted)
        assertTrue(actual.oppholdsadresser.isEmpty())
    }

}
