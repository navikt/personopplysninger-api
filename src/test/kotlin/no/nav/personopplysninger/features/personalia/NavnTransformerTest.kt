package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Navn
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class NavnTransformerTest {

    @Test
    fun aaaaaa(){
        val inbound = Navn(fornavn = "anyFornavn", mellomnavn = "anyMellomnavn", slektsnavn = "anySlektsnavn")
        val actual = NavnTransformer.toOutbound(inbound)
        assertEquals(inbound.fornavn, actual.fornavn)
        assertEquals(inbound.mellomnavn, actual.mellomnavn)
        assertEquals(inbound.slektsnavn, actual.slektsnavn)
    }

    @Test
    fun bbbbbb(){
        val inbound = Navn(fornavn = null, mellomnavn = null, slektsnavn = null)
        val actual = NavnTransformer.toOutbound(inbound)
        assertEquals(inbound.fornavn, actual.fornavn)
        assertEquals(inbound.mellomnavn, actual.mellomnavn)
        assertEquals(inbound.slektsnavn, actual.slektsnavn)
    }


}