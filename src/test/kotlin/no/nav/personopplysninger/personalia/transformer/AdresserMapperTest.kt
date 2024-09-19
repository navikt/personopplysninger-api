package no.nav.personopplysninger.personalia.transformer

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.personalia.dto.outbound.adresse.AdresseType
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createKontaktadresse
import no.nav.personopplysninger.personalia.transformer.testdata.createOppholdsadresse
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPerson
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class AdresserMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Person = defaultPerson
        val outbound: Adresser = inbound.toOutboundAdresser(defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            kontaktadresser.shouldHaveSize(1)
            bostedsadresse.shouldNotBeNull()
            oppholdsadresser.shouldHaveSize(1)
            deltBosted.shouldNotBeNull()
        }
    }

    @Test
    fun `should map all fields correctly when no addresses`() {
        val inbound: Person = defaultPerson.copy(
            oppholdsadresse = emptyList(),
            deltBosted = emptyList(),
            kontaktadresse = emptyList(),
            bostedsadresse = emptyList(),
        )
        val outbound: Adresser = inbound.toOutboundAdresser(PersonaliaKodeverk())

        assertSoftly(outbound) {
            kontaktadresser.shouldBeEmpty()
            bostedsadresse.shouldBeNull()
            oppholdsadresser.shouldBeEmpty()
            deltBosted.shouldBeNull()
        }
    }

    @Test
    fun `should map kodeverk fields correctly when multiple oppholdsadresser`() {
        val inbound: Person = defaultPerson.copy(
            oppholdsadresse = List(GENERATED_ADRESSER_SIZE) { createOppholdsadresse(AdresseType.VEGADRESSE) },
        )
        val outbound: Adresser = inbound.toOutboundAdresser(
            PersonaliaKodeverk(oppholdsadresseKodeverk = generateAdresseKodeverk())
        )

        assertSoftly(outbound.oppholdsadresser) {
            shouldHaveSize(GENERATED_ADRESSER_SIZE)
            indices.forEach {
                with(get(it).adresse as Vegadresse) {
                    kommune shouldBe "kommune $it"
                    poststed shouldBe "poststed $it"
                }
            }
        }
    }

    @Test
    fun `should map kodeverk fields correctly when multiple kontaktadresser`() {
        val inbound: Person = defaultPerson.copy(
            kontaktadresse = List(GENERATED_ADRESSER_SIZE) { createKontaktadresse(AdresseType.UTENLANDSK_ADRESSE) },
        )
        val outbound: Adresser = inbound.toOutboundAdresser(
            PersonaliaKodeverk(kontaktadresseKodeverk = generateAdresseKodeverk())
        )

        assertSoftly(outbound.kontaktadresser) {
            shouldHaveSize(GENERATED_ADRESSER_SIZE)
            indices.forEach {
                with(get(it).adresse as UtenlandskAdresse) {
                    land shouldBe "land $it"
                }
            }
        }
    }

    companion object {
        private const val GENERATED_ADRESSER_SIZE = 3

        private fun generateAdresseKodeverk() = List(GENERATED_ADRESSER_SIZE) {
            AdresseKodeverk(
                poststed = "poststed $it",
                land = "land $it",
                kommune = "kommune $it"
            )
        }
    }
}
