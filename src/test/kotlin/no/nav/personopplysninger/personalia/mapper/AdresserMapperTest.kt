package no.nav.personopplysninger.personalia.mapper

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
import no.nav.personopplysninger.personalia.mapper.testdata.createKontaktadresse
import no.nav.personopplysninger.personalia.mapper.testdata.createOppholdsadresse
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPerson
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class AdresserMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Person = defaultPerson
        val outbound: Adresser = inbound.toOutboundAdresser(defaultPersonaliaKodeverk)

        assertSoftly(outbound) {
            kontaktadresser.shouldHaveSize(inbound.kontaktadresse.size)
            bostedsadresse.shouldNotBeNull()
            oppholdsadresser.shouldHaveSize(inbound.oppholdsadresse.size)
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
        val generatedOppholdsadresser = List(GENERATED_ADRESSER_SIZE) { createOppholdsadresse(AdresseType.VEGADRESSE) }
        val generatedKodeverk = PersonaliaKodeverk(oppholdsadresseKodeverk = generateAdresseKodeverk())

        val inbound: Person = defaultPerson.copy(oppholdsadresse = generatedOppholdsadresser)
        val outbound: Adresser = inbound.toOutboundAdresser(generatedKodeverk)

        assertSoftly(outbound.oppholdsadresser) {
            shouldHaveSize(GENERATED_ADRESSER_SIZE)
            indices.forEach {
                with(get(it).adresse as Vegadresse) {
                    kommune shouldBe generatedKodeverk.oppholdsadresseKodeverk[it].kommune
                    poststed shouldBe generatedKodeverk.oppholdsadresseKodeverk[it].poststed
                }
            }
        }
    }

    @Test
    fun `should map kodeverk fields correctly when multiple kontaktadresser`() {
        val generatedKontaktadresser = List(GENERATED_ADRESSER_SIZE) { createKontaktadresse(AdresseType.UTENLANDSK_ADRESSE) }
        val generatedKodeverk = PersonaliaKodeverk(kontaktadresseKodeverk = generateAdresseKodeverk())

        val inbound: Person = defaultPerson.copy(kontaktadresse = generatedKontaktadresser)
        val outbound: Adresser = inbound.toOutboundAdresser(generatedKodeverk)

        assertSoftly(outbound.kontaktadresser) {
            shouldHaveSize(GENERATED_ADRESSER_SIZE)
            indices.forEach {
                with(get(it).adresse as UtenlandskAdresse) {
                    land shouldBe generatedKodeverk.kontaktadresseKodeverk[it].land
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
