package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.dto.transformer.AdresseinfoTransformer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = AdresseinfoObjectMother.allFieldsHaveValues

        val actual: Adresser = AdresseinfoTransformer.toOutbound(inbound)

        assertNotNull(actual.boadresse)
        assertNotNull(actual.geografiskTilknytning)
        assertNotNull(actual.postadresse)
        assertNotNull(actual.prioritertAdresse)
        assertNotNull(actual.tilleggsadresse)
        assertNotNull(actual.utenlandskAdresse)
        assertTrue(actual.datakilder.isNotEmpty())
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = AdresseinfoObjectMother.adresseinfoNullObject

        val actual = AdresseinfoTransformer.toOutbound(inbound)

        assertNull(actual.boadresse)
        assertNull(actual.geografiskTilknytning)
        assertNull(actual.postadresse)
        assertNull(actual.prioritertAdresse)
        assertNull(actual.tilleggsadresse)
        assertNull(actual.utenlandskAdresse)
        assertTrue(actual.datakilder.isEmpty())
    }

    @Test
    fun alleUnikeDatakilderSkalRegistreres() {
        // TODO Are: Verifiser at alle ulike kilder har blir plukket opp og puttet i settet "datakilder"
    }
}