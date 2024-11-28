package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.personalia.mapper.testdata.createBostedsadresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultAdresseKodeverk
import org.junit.jupiter.api.Test
import no.nav.pdl.generated.dto.hentpersonquery.Bostedsadresse as PdlBostedsadresse

class BostedsadresseMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: PdlBostedsadresse = createBostedsadresse()
        val outbound: Bostedsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        assertSoftly(outbound) {
            angittFlyttedato shouldBe inbound.angittFlyttedato
            coAdressenavn shouldBe inbound.coAdressenavn
            adresse.shouldNotBeNull()
        }
    }

    @Test
    fun `should map vegadresse`() {
        val inbound: PdlBostedsadresse = createBostedsadresse(VEGADRESSE)
        val outbound: Bostedsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe VEGADRESSE
    }

    @Test
    fun `should map matrikkeladresse`() {
        val inbound: PdlBostedsadresse = createBostedsadresse(MATRIKKELADRESSE)
        val outbound: Bostedsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe MATRIKKELADRESSE
    }

    @Test
    fun `should map utenlandsk adresse`() {
        val inbound: PdlBostedsadresse = createBostedsadresse(UTENLANDSK_ADRESSE)
        val outbound: Bostedsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UTENLANDSK_ADRESSE
    }

    @Test
    fun `should map ukjent bosted`() {
        val inbound: PdlBostedsadresse = createBostedsadresse(UKJENTBOSTED)
        val outbound: Bostedsadresse = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UKJENTBOSTED
    }

    @Test
    fun `should return null when unsupported type`() {
        val inbound: PdlBostedsadresse = createBostedsadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound: Bostedsadresse? = inbound.toOutbound(defaultAdresseKodeverk)

        outbound.shouldBeNull()
    }
}