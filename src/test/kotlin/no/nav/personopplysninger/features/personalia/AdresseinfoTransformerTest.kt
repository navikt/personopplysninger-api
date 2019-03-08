package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.dto.transformer.AdresseinfoTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = AdresseinfoObjectMother.withValuesInAllFields

        val actual: Adresser = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNotNull(actual.boadresse)
        assertNotNull(actual.geografiskTilknytning)
        assertNotNull(actual.postadresse)
        assertNotNull(actual.prioritertAdresse)
        assertNotNull(actual.tilleggsadresse)
        assertNotNull(actual.utenlandskAdresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = AdresseinfoObjectMother.adresseinfoNullObject

        val actual = AdresseinfoTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.boadresse)
        assertNull(actual.geografiskTilknytning)
        assertNull(actual.postadresse)
        assertNull(actual.prioritertAdresse)
        assertNull(actual.tilleggsadresse)
        assertNull(actual.utenlandskAdresse)
    }

}