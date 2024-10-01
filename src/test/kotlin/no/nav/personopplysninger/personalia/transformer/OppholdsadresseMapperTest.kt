package no.nav.personopplysninger.personalia.transformer

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Oppholdsadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createOppholdsadresse
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import org.junit.jupiter.api.Test
import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse as PdlOppholdsadresse

class OppholdsadresseMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: PdlOppholdsadresse = createOppholdsadresse()
        val outbound: Oppholdsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        assertSoftly(outbound) {
            gyldigTilOgMed shouldBe "1337-05-06T12:30"
            coAdressenavn shouldBe "coAdressenavn"
            adresse.shouldNotBeNull()
            kilde shouldBe "pdl"
        }
    }

    @Test
    fun `should map utenlandsk adresse`() {
        val inbound: PdlOppholdsadresse = createOppholdsadresse(UTENLANDSK_ADRESSE)
        val outbound: Oppholdsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse?.type shouldBe UTENLANDSK_ADRESSE
    }

    @Test
    fun `should map vegadresse`() {
        val inbound: PdlOppholdsadresse = createOppholdsadresse(VEGADRESSE)
        val outbound: Oppholdsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse?.type shouldBe VEGADRESSE
    }

    @Test
    fun `should map matrikkeladresse`() {
        val inbound: PdlOppholdsadresse = createOppholdsadresse(MATRIKKELADRESSE)
        val outbound: Oppholdsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse?.type shouldBe MATRIKKELADRESSE
    }

    @Test
    fun `should return null when unsupported type`() {
        val inbound: PdlOppholdsadresse = createOppholdsadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound: Oppholdsadresse? = inbound.toOutbound(defaultAdresseKodeverk)

        outbound.shouldBeNull()
    }
}