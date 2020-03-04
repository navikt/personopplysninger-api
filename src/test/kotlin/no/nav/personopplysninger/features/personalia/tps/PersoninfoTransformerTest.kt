package no.nav.personopplysninger.features.personalia.tps


import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.features.personalia.dto.transformer.PersoninfoTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlTelefonnummer
import no.nav.personopplysninger.features.personalia.pdl.pdlPersonInfoWithValues
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(PER_CLASS)
class PersoninfoTransformerTest {

    @Test
    fun gittPersonalia_skalFaaPersonalia() {
        val inbound: Personinfo = PersoninfoObjectMother.withValuesInAllFields
        val kodeverk = PersonaliaKodeverk()
        val pdlPersoninfo = pdlPersonInfoWithValues
        val actual: Personalia = PersoninfoTransformer.toOutbound(inbound, pdlPersoninfo, kodeverk)

        assertFornavn(inbound.navn!!, actual.fornavn!!)
        assertEtternavn(inbound.navn!!, actual.etternavn!!)
        assertEquals(inbound.kontonummer!!.nummer!!, actual.kontonr!!)
        assertTlfnr(pdlPersoninfo.telefonnummer, actual.tlfnr!!)
        assertEquals(inbound.ident!!, actual.personident!!.verdi)
        assertEquals(inbound.identtype!!.verdi!!, actual.personident!!.type)
    }

    private fun assertTlfnr(expected: List<PdlTelefonnummer>, actual: Tlfnr) {
        assertEquals(expected.find { it.prioritet == 1 }?.nummer, actual.mobil!!)
        assertEquals(expected.find { it.prioritet == 2 }?.nummer, actual.privat!!)
    }

    private fun assertFornavn(inbound: Navn, actual: String) {
        assertTrue(actual.startsWith(inbound.fornavn!!))
        assertTrue(actual.contains(inbound.mellomnavn!!))
    }

    private fun assertEtternavn(inbound: Navn, actual: String) {
        assertEquals(inbound.slektsnavn!!, actual)
    }
}