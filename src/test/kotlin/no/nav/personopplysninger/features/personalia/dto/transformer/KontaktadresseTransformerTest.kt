package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyKontaktadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonaliaKodeverk
import no.nav.personopplysninger.testutils.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KontaktadresseTransformerTest {

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyKontaktadresse(VEGADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = KontaktadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(actual.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, VEGADRESSE)

        val vegadresse = actual.adresse as Vegadresse

        assertVegadresseEquals(vegadresse, personaliaKodeverk.kontaktadressePostSted!!, inbound.vegadresse!!)
    }

    @Test
    fun canTransformPostadresseIFrittFormat() {
        val inbound = createDummyKontaktadresse(POSTADRESSE_I_FRITT_FORMAT)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = KontaktadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(actual.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, POSTADRESSE_I_FRITT_FORMAT)

        val postAdresseIFrittFormat = actual.adresse as PostAdresseIFrittFormat

        assertPostAdresseIFrittFormatEquals(
            postAdresseIFrittFormat,
            personaliaKodeverk.kontaktadressePostSted!!,
            inbound.postadresseIFrittFormat!!
        )
    }

    @Test
    fun canTransformPdlPostboksadresse() {
        val inbound = createDummyKontaktadresse(POSTBOKSADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = KontaktadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(actual.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, POSTBOKSADRESSE)

        val postboksadresse = actual.adresse as Postboksadresse

        assertPostboksadresseEquals(
            postboksadresse,
            personaliaKodeverk.kontaktadressePostSted!!,
            inbound.postboksadresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyKontaktadresse(UTENLANDSK_ADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = KontaktadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(actual.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE)

        val utenlandskAdresse = actual.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            personaliaKodeverk.kontaktadresseLand!!,
            inbound.utenlandskAdresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresseIFrittFormat() {
        val inbound =
            createDummyKontaktadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = KontaktadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        assertEquals(actual.gyldigFraOgMed.toString(), inbound.gyldigFraOgMed.toString())
        assertEquals(actual.gyldigTilOgMed.toString(), inbound.gyldigTilOgMed.toString())
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE_I_FRITT_FORMAT)

        val utenlandskAdresseIFrittFormat = actual.adresse as UtenlandskAdresseIFrittFormat

        assertUtenlandskAdresseIFrittFormatEquals(
            utenlandskAdresseIFrittFormat,
            personaliaKodeverk.kontaktadresseLand!!,
            inbound.utenlandskAdresseIFrittFormat!!
        )
    }
}