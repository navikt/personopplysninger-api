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
import no.nav.personopplysninger.personalia.transformer.testdata.createDeltBosted
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUkjentbostedEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeltBostedMapperTest {

    private val adresseKodeverk = defaultAdresseKodeverk

    @Test
    fun canTransformVegdresse() {
        val inbound = createDeltBosted(VEGADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, VEGADRESSE)

        val vegadresse = outbound?.adresse as Vegadresse

        assertVegadresseEquals(
            vegadresse,
            adresseKodeverk.poststed,
            adresseKodeverk.kommune,
            inbound.vegadresse
        )
    }

    @Test
    fun canTransformMatrikkeladresse() {
        val inbound = createDeltBosted(MATRIKKELADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, MATRIKKELADRESSE)

        val matrikkeladresse = outbound?.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(
            matrikkeladresse,
            adresseKodeverk.poststed,
            adresseKodeverk.kommune,
            inbound.matrikkeladresse
        )

    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDeltBosted(UTENLANDSK_ADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UTENLANDSK_ADRESSE)

        val utenlandskAdresse = outbound?.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            adresseKodeverk.land,
            inbound.utenlandskAdresse
        )
    }

    @Test
    fun canTransformUkjentbosted() {
        val inbound = createDeltBosted(UKJENTBOSTED)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UKJENTBOSTED)

        val ukjentbosted = outbound?.adresse as Ukjentbosted

        assertUkjentbostedEquals(ukjentbosted, adresseKodeverk.kommune)
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createDeltBosted(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound = inbound.toOutbound(adresseKodeverk)

        Assertions.assertNull(outbound)
    }
}