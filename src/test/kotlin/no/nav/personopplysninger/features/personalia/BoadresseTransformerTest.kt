package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.BoadresseTransformer
import no.nav.tps.person.Boadresse
import no.nav.tps.person.Matrikkeladresse
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class BoadresseTransformerTest {

    @Test
    fun gittBoadresse_skalFaaBoadresse() {
        val inbound = Boadresse(
                adresse = "Vardeveien 7",
                kommune = "Kristiansand",
                matrikkeladresse = MatrikkeladresseObjectMother.vardeveien7(),
                postnummer = "5002",
                veiadresse = VeiadresseObjectMother.vardeveien7()
        )

        val actual = BoadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse, actual.adresse)
        assertEquals(inbound.kommune, actual.kommune)
        //assertEquals(inbound.matrikkeladresse, `is`(notNullValue()))
        assertEquals(inbound.postnummer, actual.postnummer)
        //assertEquals(inbound.veiadresse , `is`(notNullValue()))
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = Boadresse(
                adresse = null,
                kommune = null,
                matrikkeladresse = MatrikkeladresseObjectMother.bareNull(),
                postnummer = null,
                veiadresse = VeiadresseObjectMother.bareNull()
        )

        val actual = BoadresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse, actual.adresse)
        assertEquals(inbound.kommune, actual.kommune)
        //assertEquals(inbound.matrikkeladresse, actual.matrikkeladresse)
        assertEquals(inbound.postnummer, actual.postnummer)
        //assertEquals(inbound.veiadresse, actual.veiadresse)
    }
}