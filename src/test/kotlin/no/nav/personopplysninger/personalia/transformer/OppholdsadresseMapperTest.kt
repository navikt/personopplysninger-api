package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createOppholdsadresse
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OppholdsadresseMapperTest {

    private val adresseKodeverk = defaultAdresseKodeverk

    @Test
    fun canTransformVegdresse() {
        val inbound = createOppholdsadresse(VEGADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, VEGADRESSE)
        assertEquals(outbound?.kilde, inbound.metadata.master)

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
        val inbound = createOppholdsadresse(MATRIKKELADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, MATRIKKELADRESSE)
        assertEquals(outbound?.kilde, inbound.metadata.master)

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
        val inbound = createOppholdsadresse(UTENLANDSK_ADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UTENLANDSK_ADRESSE)
        assertEquals(outbound?.kilde, inbound.metadata.master)

        val utenlandskAdresse = outbound?.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            adresseKodeverk.land,
            inbound.utenlandskAdresse
        )
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createOppholdsadresse(UKJENTBOSTED)
        val outbound = inbound.toOutbound(adresseKodeverk)

        Assertions.assertNull(outbound)
    }
}