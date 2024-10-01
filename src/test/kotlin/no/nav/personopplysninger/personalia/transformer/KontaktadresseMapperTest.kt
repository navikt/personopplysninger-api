package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createKontaktadresse
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import no.nav.personopplysninger.testutils.assertPostAdresseIFrittFormatEquals
import no.nav.personopplysninger.testutils.assertPostboksadresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseIFrittFormatEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class KontaktadresseMapperTest {

    private val adresseKodeverk = defaultAdresseKodeverk

    @Test
    fun canTransformVegdresse() {
        val inbound = createKontaktadresse(VEGADRESSE)
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
    fun canTransformPostadresseIFrittFormat() {
        val inbound = createKontaktadresse(POSTADRESSE_I_FRITT_FORMAT)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, POSTADRESSE_I_FRITT_FORMAT)
        assertEquals(outbound?.kilde, inbound.metadata.master)

        val postAdresseIFrittFormat = outbound?.adresse as PostAdresseIFrittFormat

        assertPostAdresseIFrittFormatEquals(
            postAdresseIFrittFormat,
            adresseKodeverk.poststed,
            inbound.postadresseIFrittFormat
        )
    }

    @Test
    fun canTransformPdlPostboksadresse() {
        val inbound = createKontaktadresse(POSTBOKSADRESSE)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, POSTBOKSADRESSE)
        assertEquals(outbound?.kilde, inbound.metadata.master)

        val postboksadresse = outbound?.adresse as Postboksadresse

        assertPostboksadresseEquals(
            postboksadresse,
            adresseKodeverk.poststed,
            inbound.postboksadresse
        )
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createKontaktadresse(UTENLANDSK_ADRESSE)
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
    fun canTransformUtenlandskAdresseIFrittFormat() {
        val inbound =
            createKontaktadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertEquals(outbound?.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(outbound?.coAdressenavn, inbound.coAdressenavn)
        assertEquals(outbound?.adresse?.type, UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        assertEquals(outbound?.kilde, inbound.metadata.master)

        val utenlandskAdresseIFrittFormat = outbound?.adresse as UtenlandskAdresseIFrittFormat

        assertUtenlandskAdresseIFrittFormatEquals(
            utenlandskAdresseIFrittFormat,
            adresseKodeverk.land,
            inbound.utenlandskAdresseIFrittFormat
        )
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createKontaktadresse(UKJENTBOSTED)
        val outbound = inbound.toOutbound(adresseKodeverk)

        assertNull(outbound)
    }
}