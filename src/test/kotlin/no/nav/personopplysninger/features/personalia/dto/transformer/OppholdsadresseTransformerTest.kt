package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.AdresseType.*
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyOppholdsadresse
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonaliaKodeverk
import no.nav.personopplysninger.testutils.assertMatrikkeladresseEquals
import no.nav.personopplysninger.testutils.assertUtenlandskAdresseEquals
import no.nav.personopplysninger.testutils.assertVegadresseEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OppholdsadresseTransformerTest {

    @Test
    fun canTransformVegdresse() {
        val inbound = createDummyOppholdsadresse(VEGADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = OppholdsadresseTransformer.toOutbound(inbound, personaliaKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, VEGADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val vegadresse = actual.adresse as Vegadresse

        assertVegadresseEquals(
            vegadresse,
            personaliaKodeverk.oppholdsadressePostSted!!,
            personaliaKodeverk.oppholdsadresseKommune!!,
            inbound.vegadresse!!
        )
    }

    @Test
    fun canTransformMatrikkeladresse() {
        val inbound = createDummyOppholdsadresse(MATRIKKELADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = OppholdsadresseTransformer.toOutbound(inbound, personaliaKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, MATRIKKELADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val matrikkeladresse = actual.adresse as Matrikkeladresse

        assertMatrikkeladresseEquals(
            matrikkeladresse,
            personaliaKodeverk.oppholdsadressePostSted!!,
            personaliaKodeverk.oppholdsadresseKommune!!,
            inbound.matrikkeladresse!!
        )
    }

    @Test
    fun canTransformUtenlandskAdresse() {
        val inbound = createDummyOppholdsadresse(UTENLANDSK_ADRESSE)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = OppholdsadresseTransformer.toOutbound(inbound, personaliaKodeverk)!!

        assertEquals(actual.oppholdAnnetSted, inbound.oppholdAnnetSted)
        assertEquals(actual.gyldigFraOgMed, inbound.gyldigFraOgMed)
        assertEquals(actual.gyldigTilOgMed, inbound.gyldigTilOgMed)
        assertEquals(actual.coAdressenavn, inbound.coAdressenavn)
        assertEquals(actual.adresse.type, UTENLANDSK_ADRESSE)
        assertEquals(actual.kilde, inbound.metadata.master)

        val utenlandskAdresse = actual.adresse as UtenlandskAdresse

        assertUtenlandskAdresseEquals(
            utenlandskAdresse,
            personaliaKodeverk.oppholdsadresseLand!!,
            inbound.utenlandskAdresse!!
        )
    }

    @Test
    fun unsupportedAdresseTypeReturnsNull() {
        val inbound = createDummyOppholdsadresse(UKJENTBOSTED)

        val personaliaKodeverk = createDummyPersonaliaKodeverk()

        val actual = OppholdsadresseTransformer.toOutbound(inbound, personaliaKodeverk)

        Assertions.assertNull(actual)
    }
}