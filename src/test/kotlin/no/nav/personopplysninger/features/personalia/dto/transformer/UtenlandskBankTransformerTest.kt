package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.UtenlandskBankObjectMother
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class UtenlandskBankTransformerTest {

    @Test
    fun gittUtenlandskBank_skalFaaUtenlandskBank() {
        val inbound = UtenlandskBankObjectMother.utenlandskTestbank()

        val actual = UtenlandskBankTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertEquals(inbound.adresse1, actual.adresse1)
        assertEquals(inbound.adresse2, actual.adresse2)
        assertEquals(inbound.adresse3, actual.adresse3)
        assertEquals(inbound.bankkode, actual.bankkode)
        assertEquals(inbound.banknavn, actual.banknavn)
        assertEquals(inbound.iban, actual.iban)
        assertEquals(inbound.swiftkode, actual.swiftkode)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = UtenlandskBankObjectMother.utenlandskBankNullObject()

        val actual = UtenlandskBankTransformer.toOutbound(inbound, kodeverk = PersonaliaKodeverk())

        assertNull(actual.adresse1)
        assertNull(actual.adresse2)
        assertNull(actual.adresse3)
        assertNull(actual.bankkode)
        assertNull(actual.banknavn)
    }
}
