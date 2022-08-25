package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyAdresseKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyOppholdsadresse
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OppholdsadresseTransformerTest {

    private val adresseKodeverk = createDummyAdresseKodeverk()

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