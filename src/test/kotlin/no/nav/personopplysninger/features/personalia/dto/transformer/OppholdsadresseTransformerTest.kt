package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyAdresseKodeverk
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyOppholdsadresse
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OppholdsadresseTransformerTest {

    val adresseKodeverk = createDummyAdresseKodeverk()

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyOppholdsadresse(VEGADRESSE)
        val actual = OppholdsadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse?.type, VEGADRESSE)
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
    fun canTransformMatrikkeladresse() {
        val inbound = createDummyOppholdsadresse(MATRIKKELADRESSE)
        val actual = OppholdsadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse?.type, MATRIKKELADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val matrikkeladresse = actual.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(
            matrikkeladresse,
            adresseKodeverk.poststed!!,
            adresseKodeverk.kommune!!,
            inbound.matrikkeladresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyOppholdsadresse(UTENLANDSK_ADRESSE)
        val actual = OppholdsadresseTransformer.toOutbound(inbound, adresseKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse?.type, UTENLANDSK_ADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val utenlandskAdresse = actual.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            adresseKodeverk.land!!,
            inbound.utenlandskAdresse!!
        )
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createDummyOppholdsadresse(UKJENTBOSTED)
        val actual = OppholdsadresseTransformer.toOutbound(inbound, adresseKodeverk)

        Assertions.assertNull(actual)
    }
}