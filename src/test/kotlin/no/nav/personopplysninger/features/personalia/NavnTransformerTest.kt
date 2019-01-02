package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.NavnTransformer
import no.nav.tps.person.Navn
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class NavnTransformerTest {

//    @Test
//    fun gittNavn_skalFaaNavn() {
//        val inbound = Navn(fornavn = "anyFornavn", mellomnavn = "anyMellomnavn", slektsnavn = "anySlektsnavn", kilde = "anyKilde")
//
//        val actual = NavnTransformer.toOutbound(inbound)
//
//        actual
//    }
//
//    @Test
//    fun gittNull_skalFaaNull() {
//        val inbound = Navn(fornavn = null, mellomnavn = null, slektsnavn = null, kilde = null)
//
//        val actual = NavnTransformer.toOutbound(inbound)
//
//        assertEquals(inbound, actual)
//    }
//
//    private fun assertEquals(expected: Navn, actual: no.nav.personopplysninger.features.personalia.dto.outbound.Navn) {
//        assertEquals(expected.fornavn, actual.fornavn)
//        assertEquals(expected.mellomnavn, actual.mellomnavn)
//        assertEquals(expected.slektsnavn, actual.slektsnavn)
//        assertEquals(expected.kilde, actual.kilde)
//    }

}