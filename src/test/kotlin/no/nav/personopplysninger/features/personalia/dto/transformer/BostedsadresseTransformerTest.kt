package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyBostedsadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonaliaKodeverk
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUkjentbostedEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BostedsadresseTransformerTest {

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyBostedsadresse(VEGADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = BostedsadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, VEGADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val vegadresse = actual.adresse as Vegadresse

        assertVegadresseEquals(vegadresse, personaliaKodeverk.bostedsadressePostSted!!, inbound.vegadresse!!)
    }

    @Test
    fun canTransformMatrikkeladresse() {
        val inbound = createDummyBostedsadresse(MATRIKKELADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = BostedsadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, MATRIKKELADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val matrikkeladresse = actual.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(matrikkeladresse, personaliaKodeverk.bostedsadressePostSted!!, inbound.matrikkeladresse!!)

    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyBostedsadresse(UTENLANDSK_ADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = BostedsadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val utenlandskAdresse = actual.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(utenlandskAdresse, personaliaKodeverk.bostedsadresseLand!!, inbound.utenlandskAdresse!!)
    }

    @Test
    fun canTransformUkjentbosted() {
        val inbound = createDummyBostedsadresse(UKJENTBOSTED)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = BostedsadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UKJENTBOSTED)
        assertEquals(actual.kilde, inbound.metadata.master)

        val ukjentbosted = actual.adresse as Ukjentbosted

        assertUkjentbostedEquals(ukjentbosted, inbound.ukjentBosted!!)
    }

    @Test
    fun unsupportedAdresseTypeThrowsException() {
        val inbound = createDummyBostedsadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        assertThrows(IllegalStateException::class.java) {
            BostedsadresseTransformer.toOutbound(inbound, personaliaKodeverk)
        }
    }
}