package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultMatrikkeladresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPostadresseIFrittFormat
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPostboksadresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultUtenlandskAdresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultUtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.mapper.testdata.defaultVegadresse
import org.junit.jupiter.api.Test

class AdresseMapperTest {

    @Test
    fun `should map vegadresse correctly`() {
        val outbound: Vegadresse = defaultVegadresse.toOutbound(POSTSTED, KOMMUNE)

        assertSoftly(outbound) {
            husnummer shouldBe "dummy husnummer"
            husbokstav shouldBe "dummy husbokstav"
            bruksenhetsnummer shouldBe "dummy bruksenhetsnummer"
            adressenavn shouldBe "dummy adressenavn"
            kommune shouldBe KOMMUNE
            tilleggsnavn shouldBe "dummy tilleggsnavn"
            postnummer shouldBe "dummy postnummer"
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map postadresse i fritt format correctly`() {
        val outbound: PostAdresseIFrittFormat = defaultPostadresseIFrittFormat.toOutbound(POSTSTED)

        assertSoftly(outbound) {
            adresselinje1 shouldBe "dummy adresselinje1"
            adresselinje2 shouldBe "dummy adresselinje2"
            adresselinje3 shouldBe "dummy adresselinje3"
            postnummer shouldBe "dummy postnummer"
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map postboksadresse correctly`() {
        val outbound: Postboksadresse = defaultPostboksadresse.toOutbound(POSTSTED)

        assertSoftly(outbound) {
            postbokseier shouldBe "dummy postbokseier"
            postboks shouldBe "dummy postboks"
            postnummer shouldBe "dummy postnummer"
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map utenlandsk adresse correctly`() {
        val outbound: UtenlandskAdresse = defaultUtenlandskAdresse.toOutbound(LAND)

        assertSoftly(outbound) {
            adressenavnNummer shouldBe "dummy adressenavnNummer"
            bygningEtasjeLeilighet shouldBe "dummy bygningEtasjeLeilighet"
            postboksNummerNavn shouldBe "dummy postboksNummerNavn"
            postkode shouldBe "dummy postkode"
            bySted shouldBe "dummy bySted"
            regionDistriktOmraade shouldBe "dummy regionDistriktOmraade"
            land shouldBe LAND
        }
    }

    @Test
    fun `should map utenlandsk adresse i fritt format correctly`() {
        val outbound: UtenlandskAdresseIFrittFormat = defaultUtenlandskAdresseIFrittFormat.toOutbound(LAND)

        assertSoftly(outbound) {
            adresselinje1 shouldBe "dummy adresselinje1"
            adresselinje2 shouldBe "dummy adresselinje2"
            adresselinje3 shouldBe "dummy adresselinje3"
            land shouldBe LAND
        }
    }

    @Test
    fun `should map matrikkeladresse correctly`() {
        val outbound: Matrikkeladresse = defaultMatrikkeladresse.toOutbound(POSTSTED, KOMMUNE)

        assertSoftly(outbound) {
            bruksenhetsnummer shouldBe "dummy bruksenhetsnummer"
            tilleggsnavn shouldBe "dummy tilleggsnavn"
            postnummer shouldBe "dummy postnummer"
            poststed shouldBe POSTSTED
            kommune shouldBe KOMMUNE
        }
    }

    companion object {
        private const val POSTSTED = "poststed"
        private const val KOMMUNE = "kommune"
        private const val LAND = "land"
    }
}
