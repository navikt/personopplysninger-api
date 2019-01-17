package no.nav.personopplysninger.features.personalia


import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.features.personalia.dto.transformer.PersoninfoTransformer
import no.nav.tps.person.Kode
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import no.nav.tps.person.Telefoninfo
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

        val actual: Personalia = PersoninfoTransformer.toOutbound(inbound)

        assertFornavn(inbound.navn!!, actual.fornavn!!)
        assertEtternavn(inbound.navn!!, actual.etternavn!!)
        assertEquals(inbound.kontonummer!!.nummer!!, actual.kontonr!!)
        assertTlfnr(inbound.telefon!!, actual.tlfnr!!)
        assertEquals(inbound.spraak!!.kode!!.verdi!!, actual.spraak!!) // TODO Are IN-708: Kodeverk. Husk kilde.
        assertEquals("TODO", actual.epostadr) // TODO Are: Egen integrasjon for å finne epost? Husk å registrer datakilden.
        assertEquals("Fødselsregistrert", actual.personstatus) // TODO Are: Kodeverk. Husk kilde.
        assertEquals(inbound.statsborgerskap!!.kode!!.verdi!!, actual.statsborgerskap) // TODO Are: Kodeverk. Husk kilde.
        assertFoedested(inbound.foedtIKommune!!, "NORGE", actual.foedested!!)
        assertEquals("Gift", actual.sivilstand)// TODO Are: Kodeverk. Husk kilde.
        assertEquals("Mann", actual.kjoenn)
        assertEquals(inbound.ident!!, actual.personident!!.verdi)
        assertEquals(inbound.identtype!!.verdi!!, actual.personident!!.type)
    }

    private fun assertFoedested(expectedKommune: Kode, expectedLand: String, actualFoedested: String) {
        // TODO Are: Kodeverk.
        val expected = expectedKommune.verdi!! + ", " + expectedLand
        assertEquals(expected, actualFoedested, "Fødested skal ha formen '<kommunenavn>, <landnavn>' (uten fnutter)")
    }

    private fun assertTlfnr(expected: Telefoninfo, actual: Tlfnr) {
        assertEquals(expected.jobb!!, actual.jobb!!)
        assertEquals(expected.mobil!!, actual.mobil!!)
        assertEquals(expected.privat!!, actual.privat!!)
    }

    private fun assertFornavn(inbound: Navn, actual: String) {
        assertTrue(actual.startsWith(inbound.fornavn!!))
        assertTrue(actual.contains(inbound.mellomnavn!!))
    }

    private fun assertEtternavn(inbound: Navn, actual: String) {
        assertEquals(inbound.slektsnavn!!, actual)
    }

    @Test
    fun alleUnikeDatakilderSkalRegistreres() {
        // TODO Are: Verifiser at alle ulike kilder har blir plukket opp og puttet i settet "datakilder"
    }
}




