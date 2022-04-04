package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyAdresseKodeverk
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyKontaktadresse
import no.nav.personopplysninger.testutils.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktadresseTransformerTest {

    private val adresseKodeverk = createDummyAdresseKodeverk()

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyKontaktadresse(VEGADRESSE)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, VEGADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val vegadresse = actual.adresse as Vegadresse

        assertVegadresseEquals(
            vegadresse,
            adresseKodeverk.poststed!!,
            adresseKodeverk.kommune!!,
            inbound.vegadresse!!
        )
    }

    @Test
    fun canTransformPostadresseIFrittFormat() {
        val inbound = createDummyKontaktadresse(POSTADRESSE_I_FRITT_FORMAT)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, POSTADRESSE_I_FRITT_FORMAT)
        assertEquals(actual.kilde, inbound.metadata.master)

        val postAdresseIFrittFormat = actual.adresse as PostAdresseIFrittFormat

        assertPostAdresseIFrittFormatEquals(
            postAdresseIFrittFormat,
            adresseKodeverk.poststed!!,
            inbound.postadresseIFrittFormat!!
        )
    }

    @Test
    fun canTransformPdlPostboksadresse() {
        val inbound = createDummyKontaktadresse(POSTBOKSADRESSE)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, POSTBOKSADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val postboksadresse = actual.adresse as Postboksadresse

        assertPostboksadresseEquals(
            postboksadresse,
            adresseKodeverk.poststed!!,
            inbound.postboksadresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyKontaktadresse(UTENLANDSK_ADRESSE)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val utenlandskAdresse = actual.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            adresseKodeverk.land!!,
            inbound.utenlandskAdresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresseIFrittFormat() {
        val inbound =
            createDummyKontaktadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        assertEquals(actual.kilde, inbound.metadata.master)

        val utenlandskAdresseIFrittFormat = actual.adresse as UtenlandskAdresseIFrittFormat

        assertUtenlandskAdresseIFrittFormatEquals(
            utenlandskAdresseIFrittFormat,
            adresseKodeverk.land!!,
            inbound.utenlandskAdresseIFrittFormat!!
        )
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createDummyKontaktadresse(UKJENTBOSTED)
        val actual = KontaktadresseTransformer.toOutbound(inbound, adresseKodeverk)

        assertNull(actual)
    }
}