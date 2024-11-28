package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.POSTBOKSADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.personalia.mapper.testdata.createKontaktadresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultAdresseKodeverk
import org.junit.jupiter.api.Test
import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse as PdlKontaktadresse

class KontaktadresseMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: PdlKontaktadresse = createKontaktadresse()
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        assertSoftly(outbound) {
            gyldigTilOgMed shouldBe inbound.gyldigTilOgMed
            coAdressenavn shouldBe inbound.coAdressenavn
            adresse.shouldNotBeNull()
            kilde shouldBe inbound.metadata.master.lowercase()
        }
    }

    @Test
    fun `should map postboksadresse`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(POSTBOKSADRESSE)
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe POSTBOKSADRESSE
    }

    @Test
    fun `should map vegadresse`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(VEGADRESSE)
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe VEGADRESSE
    }

    @Test
    fun `should map postadresse i fritt format`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(POSTADRESSE_I_FRITT_FORMAT)
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe POSTADRESSE_I_FRITT_FORMAT
    }

    @Test
    fun `should map utenlandsk adresse`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(UTENLANDSK_ADRESSE)
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UTENLANDSK_ADRESSE
    }

    @Test
    fun `should map utenlandsk adresse i fritt format`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound: Kontaktadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UTENLANDSK_ADRESSE_I_FRITT_FORMAT
    }

    @Test
    fun `should return null when unsupported type`() {
        val inbound: PdlKontaktadresse = createKontaktadresse(UKJENTBOSTED)
        val outbound: Kontaktadresse? = inbound.toOutbound(defaultAdresseKodeverk)

        outbound.shouldBeNull()
    }
}