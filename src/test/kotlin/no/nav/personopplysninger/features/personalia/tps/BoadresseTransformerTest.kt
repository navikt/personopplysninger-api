package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.dto.transformer.BoadresseTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class BoadresseTransformerTest {

    @Test
    fun gittBoadresse_skalFaaBoadresse() {
        val inbound = BoadresseObjectMother.vardeveien7()

        val actual = BoadresseTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertEquals(inbound.adresse!!, actual.adresse)
        assertEquals(inbound.adressetillegg!!, actual.adressetillegg)
        assertEquals(inbound.bydel!!, actual.bydel)
        assertEquals(inbound.datoFraOgMed!!, actual.datoFraOgMed)
        assertEquals(" ", actual.kommune)
        assertEquals(" ", actual.land)
        assertNotNull(actual.matrikkeladresse)
        assertEquals(inbound.postnummer!!, actual.postnummer)
        Assertions.assertEquals(" ", actual.poststed)
        assertNotNull(actual.veiadresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = BoadresseObjectMother.boadresseNullObject()

        val actual = BoadresseTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.adresse)
        assertNull(actual.adressetillegg)
        assertNull(actual.bydel)
        assertNull(actual.datoFraOgMed)
        assertNull(actual.matrikkeladresse)
        assertNull(actual.postnummer)
        assertNull(actual.veiadresse)
    }
}
