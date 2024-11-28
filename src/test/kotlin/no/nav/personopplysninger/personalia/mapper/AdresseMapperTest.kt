package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostadresseIFrittFormat
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
import no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse as PdlMatrikkeladresse
import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat as PdlPostadresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse as PdlPostboksadresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse as PdlUtenlandskAdresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat as PdlUtenlandskAdresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Vegadresse as PdlVegadresse

class AdresseMapperTest {

    @Test
    fun `should map vegadresse correctly`() {
        val inbound: PdlVegadresse = defaultVegadresse
        val outbound: Vegadresse = inbound.toOutbound(POSTSTED, KOMMUNE)

        assertSoftly(outbound) {
            husnummer shouldBe inbound.husnummer
            husbokstav shouldBe inbound.husbokstav
            bruksenhetsnummer shouldBe inbound.bruksenhetsnummer
            adressenavn shouldBe inbound.adressenavn
            kommune shouldBe KOMMUNE
            tilleggsnavn shouldBe inbound.tilleggsnavn
            postnummer shouldBe inbound.postnummer
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map postadresse i fritt format correctly`() {
        val inbound: PdlPostadresseIFrittFormat = defaultPostadresseIFrittFormat
        val outbound: PostadresseIFrittFormat = inbound.toOutbound(POSTSTED)

        assertSoftly(outbound) {
            adresselinje1 shouldBe inbound.adresselinje1
            adresselinje2 shouldBe inbound.adresselinje2
            adresselinje3 shouldBe inbound.adresselinje3
            postnummer shouldBe inbound.postnummer
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map postboksadresse correctly`() {
        val inbound: PdlPostboksadresse = defaultPostboksadresse
        val outbound: Postboksadresse = inbound.toOutbound(POSTSTED)

        assertSoftly(outbound) {
            postbokseier shouldBe inbound.postbokseier
            postboks shouldBe inbound.postboks
            postnummer shouldBe inbound.postnummer
            poststed shouldBe POSTSTED
        }
    }

    @Test
    fun `should map utenlandsk adresse correctly`() {
        val inbound: PdlUtenlandskAdresse = defaultUtenlandskAdresse
        val outbound: UtenlandskAdresse = inbound.toOutbound(LAND)

        assertSoftly(outbound) {
            adressenavnNummer shouldBe inbound.adressenavnNummer
            bygningEtasjeLeilighet shouldBe inbound.bygningEtasjeLeilighet
            postboksNummerNavn shouldBe inbound.postboksNummerNavn
            postkode shouldBe inbound.postkode
            bySted shouldBe inbound.bySted
            regionDistriktOmraade shouldBe inbound.regionDistriktOmraade
            land shouldBe LAND
        }
    }

    @Test
    fun `should map utenlandsk adresse i fritt format correctly`() {
        val inbound: PdlUtenlandskAdresseIFrittFormat = defaultUtenlandskAdresseIFrittFormat
        val outbound: UtenlandskAdresseIFrittFormat = inbound.toOutbound(LAND)

        assertSoftly(outbound) {
            adresselinje1 shouldBe inbound.adresselinje1
            adresselinje2 shouldBe inbound.adresselinje2
            adresselinje3 shouldBe inbound.adresselinje3
            land shouldBe LAND
        }
    }

    @Test
    fun `should map matrikkeladresse correctly`() {
        val inbound: PdlMatrikkeladresse = defaultMatrikkeladresse
        val outbound: Matrikkeladresse = inbound.toOutbound(POSTSTED, KOMMUNE)

        assertSoftly(outbound) {
            bruksenhetsnummer shouldBe inbound.bruksenhetsnummer
            tilleggsnavn shouldBe inbound.tilleggsnavn
            postnummer shouldBe inbound.postnummer
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
