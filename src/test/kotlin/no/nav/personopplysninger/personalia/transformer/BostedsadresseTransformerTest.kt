package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyAdresseKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyBostedsadresse
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUkjentbostedEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BostedsadresseTransformerTest {

    private val adresseKodeverk = createDummyAdresseKodeverk()

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyBostedsadresse(VEGADRESSE)
        val actual = BostedsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        assertEquals(actual?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual?.adresse?.type, VEGADRESSE)

        val vegadresse = actual?.adresse as Vegadresse

        assertVegadresseEquals(
            vegadresse,
            adresseKodeverk.poststed,
            adresseKodeverk.kommune,
            inbound.vegadresse
        )
    }

    @Test
    fun canTransformMatrikkeladresse() {
        val inbound = createDummyBostedsadresse(MATRIKKELADRESSE)
        val actual = BostedsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        assertEquals(actual?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual?.adresse?.type, MATRIKKELADRESSE)

        val matrikkeladresse = actual?.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(
            matrikkeladresse,
            adresseKodeverk.poststed,
            adresseKodeverk.kommune,
            inbound.matrikkeladresse
        )

    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyBostedsadresse(UTENLANDSK_ADRESSE)
        val actual = BostedsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        assertEquals(actual?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual?.adresse?.type, UTENLANDSK_ADRESSE)

        val utenlandskAdresse = actual?.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            adresseKodeverk.land,
            inbound.utenlandskAdresse
        )
    }

    @Test
    fun canTransformUkjentbosted() {
        val inbound = createDummyBostedsadresse(UKJENTBOSTED)
        val actual = BostedsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        assertEquals(actual?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(actual?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual?.adresse?.type, UKJENTBOSTED)

        val ukjentbosted = actual?.adresse as Ukjentbosted

        assertUkjentbostedEquals(ukjentbosted, adresseKodeverk.kommune)
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createDummyBostedsadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val actual = BostedsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        Assertions.assertNull(actual)
    }
}