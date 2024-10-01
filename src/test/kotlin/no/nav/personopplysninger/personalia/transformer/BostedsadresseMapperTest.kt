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
import no.nav.personopplysninger.personalia.transformer.testdata.createBostedsadresse
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUkjentbostedEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BostedsadresseMapperTest {

    @Test
    fun canTransformVegdresse() {
        val inbound = createBostedsadresse(VEGADRESSE)
        val outbound = inbound.toOutbound(defaultAdresseKodeverk)

        assertEquals(outbound?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, VEGADRESSE)

        val vegadresse = outbound?.adresse as Vegadresse

        assertVegadresseEquals(
            vegadresse,
            defaultAdresseKodeverk.poststed,
            defaultAdresseKodeverk.kommune,
            inbound.vegadresse
        )
    }

    @Test
    fun canTransformMatrikkeladresse() {
        val inbound = createBostedsadresse(MATRIKKELADRESSE)
        val outbound = inbound.toOutbound(defaultAdresseKodeverk)

        assertEquals(outbound?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, MATRIKKELADRESSE)

        val matrikkeladresse = outbound?.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(
            matrikkeladresse,
            defaultAdresseKodeverk.poststed,
            defaultAdresseKodeverk.kommune,
            inbound.matrikkeladresse
        )

    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createBostedsadresse(UTENLANDSK_ADRESSE)
        val outbound = inbound.toOutbound(defaultAdresseKodeverk)

        assertEquals(outbound?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UTENLANDSK_ADRESSE)

        val utenlandskAdresse = outbound?.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            defaultAdresseKodeverk.land,
            inbound.utenlandskAdresse
        )
    }

    @Test
    fun canTransformUkjentbosted() {
        val inbound = createBostedsadresse(UKJENTBOSTED)
        val outbound = inbound.toOutbound(defaultAdresseKodeverk)

        assertEquals(outbound?.angittFlyttedato, inbound.angittFlyttedato)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UKJENTBOSTED)

        val ukjentbosted = outbound?.adresse as Ukjentbosted

        assertUkjentbostedEquals(ukjentbosted, defaultAdresseKodeverk.kommune)
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createBostedsadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound = inbound.toOutbound(defaultAdresseKodeverk)

        Assertions.assertNull(outbound)
    }
}