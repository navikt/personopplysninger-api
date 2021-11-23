package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.AdresseinfoObjectMother
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.PdlKontaktadresseObjectMother
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
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

        val inboundPdl = listOf(PdlKontaktadresseObjectMother.dummyVegadresse())

        val actual = AdresseinfoTransformer.toOutbound(inbound, inboundPdl, kodeverk = PersonaliaKodeverk())!!

        assertNotNull(actual.boadresse)
        assertNotNull(actual.geografiskTilknytning)
        assertNotNull(actual.postadresse)
        assertNotNull(actual.kontaktadresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = AdresseinfoObjectMother.adresseinfoNullObject

        val inboundPdl = emptyList<PdlKontaktadresse>()

        val actual = AdresseinfoTransformer.toOutbound(inbound, inboundPdl, kodeverk = PersonaliaKodeverk())!!

        assertNull(actual.boadresse)
        assertNull(actual.geografiskTilknytning)
        assertNull(actual.postadresse)
        assertNull(actual.kontaktadresse)
    }

}
