package no.nav.personopplysninger.personalia.transformer

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.MATRIKKELADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UKJENTBOSTED
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.UTENLANDSK_ADRESSE_I_FRITT_FORMAT
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType.VEGADRESSE
import no.nav.personopplysninger.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.personalia.transformer.testdata.createDeltBosted
import no.nav.personopplysninger.personalia.transformer.testdata.defaultAdresseKodeverk
import org.junit.jupiter.api.Test
import no.nav.pdl.generated.dto.hentpersonquery.DeltBosted as PdlDeltBosted

class DeltBostedMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: PdlDeltBosted = createDeltBosted()
        val outbound: DeltBosted = inbound.toOutbound(defaultAdresseKodeverk)!!

        assertSoftly(outbound) {
            coAdressenavn shouldBe "coAdressenavn"
            adresse.shouldNotBeNull()
        }
    }

    @Test
    fun `should map vegadresse`() {
        val inbound: PdlDeltBosted = createDeltBosted(VEGADRESSE)
        val outbound: DeltBosted = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe VEGADRESSE
    }

    @Test
    fun `should map matrikkeladresse`() {
        val inbound: PdlDeltBosted = createDeltBosted(MATRIKKELADRESSE)
        val outbound: DeltBosted = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe MATRIKKELADRESSE
    }

    @Test
    fun `should map utenlandsk adresse`() {
        val inbound: PdlDeltBosted = createDeltBosted(UTENLANDSK_ADRESSE)
        val outbound: DeltBosted = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UTENLANDSK_ADRESSE
    }

    @Test
    fun `should map ukjent bosted`() {
        val inbound: PdlDeltBosted = createDeltBosted(UKJENTBOSTED)
        val outbound: DeltBosted = inbound.toOutbound(defaultAdresseKodeverk)!!

        outbound.adresse.type shouldBe UKJENTBOSTED
    }

    @Test
    fun `should return null when unsupported type`() {
        val inbound: PdlDeltBosted = createDeltBosted(UTENLANDSK_ADRESSE_I_FRITT_FORMAT)
        val outbound: DeltBosted? = inbound.toOutbound(defaultAdresseKodeverk)

        outbound.shouldBeNull()
    }
}