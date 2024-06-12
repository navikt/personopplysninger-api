package no.nav.personopplysninger.personalia

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.hentpersonquery.Statsborgerskap
import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.personalia.extensions.kommunenummer
import no.nav.personopplysninger.personalia.extensions.landkode
import no.nav.personopplysninger.personalia.extensions.postnummer
import no.nav.personopplysninger.personalia.transformer.PersonaliaOgAdresserTransformer
import java.time.LocalDate

class PersonaliaService(
    private val kodeverkConsumer: KodeverkConsumer,
    private val norg2Consumer: Norg2Consumer,
    private val kontoregisterConsumer: KontoregisterConsumer,
    private val pdlConsumer: PdlConsumer
) {

    suspend fun hentPersoninfo(token: String, fodselsnr: String): PersonaliaOgAdresser {
        val pdlPersonInfo = pdlConsumer.hentPerson(token, fodselsnr)

        val konto = kontoregisterConsumer.hentAktivKonto(token, fodselsnr)

        val geografiskTilknytning = pdlPersonInfo.geografiskTilknytning.let { it?.gtBydel ?: it?.gtKommune }

        val enhetKontaktInformasjon = geografiskTilknytning
            ?.let { norg2Consumer.hentEnhet(token, it) }
            ?.let { norg2Consumer.hentKontaktinfo(token, it.enhetNr) }

        val kodeverk = createPersonaliaKodeverk(pdlPersonInfo, konto)

        return PersonaliaOgAdresserTransformer.toOutbound(pdlPersonInfo, konto, kodeverk, enhetKontaktInformasjon)
    }

    private suspend fun createPersonaliaKodeverk(
        inboundPdl: HentPersonQuery.Result,
        inboundKonto: Konto?
    ): PersonaliaKodeverk {
        val person = inboundPdl.person!!
        val geografiskTilknytning = inboundPdl.geografiskTilknytning

        val foedested = person.foedested.firstOrNull()

        val kontaktadresse = person.kontaktadresse
        val bostedsadresse = person.bostedsadresse.firstOrNull()
        val deltBosted = person.deltBosted.firstOrNull()
        val oppholdsadresse = person.oppholdsadresse

        return PersonaliaKodeverk(
            foedekommuneterm = hentKommuneKodeverksTerm(foedested?.foedekommune),
            foedelandterm = hentLandKodeverksTerm(foedested?.foedeland),
            gtLandterm = hentLandKodeverksTerm(geografiskTilknytning?.gtLand),
            statsborgerskaptermer = hentGyldigeStatsborgerskap(person.statsborgerskap),
            utenlandskbanklandterm = inboundKonto?.utenlandskKontoInfo?.let { hentLandKontoregisterKodeterm(it.bankLandkode) },
            utenlandskbankvalutaterm = inboundKonto?.utenlandskKontoInfo?.let { hentValutaKontoregisterKodeterm(it.valutakode) },
            kontaktadresseKodeverk = kontaktadresse.map {
                hentAdresseKodeverk(
                    it.postnummer,
                    it.landkode,
                    it.kommunenummer
                )
            },
            bostedsadresseKodeverk = hentAdresseKodeverk(
                bostedsadresse?.postnummer,
                bostedsadresse?.landkode,
                bostedsadresse?.kommunenummer
            ),
            deltBostedKodeverk = hentAdresseKodeverk(
                deltBosted?.postnummer,
                deltBosted?.landkode,
                deltBosted?.kommunenummer
            ),
            oppholdsadresseKodeverk = oppholdsadresse.map {
                hentAdresseKodeverk(
                    it.postnummer,
                    it.landkode,
                    it.kommunenummer
                )
            },
        )
    }

    private suspend fun hentGyldigeStatsborgerskap(statsborgerskap: List<Statsborgerskap>): List<String> {
        return statsborgerskap
            .filter { it.land != UKJENT_LAND && it.isValid() } // Filtrer ut ukjent og ugyldige
            .map { kodeverkConsumer.hentStatsborgerskap().term(it.land) }
            .filter { it.isNotEmpty() }
    }

    private fun Statsborgerskap.isValid(): Boolean {
        return this.gyldigTilOgMed.let { it == null || LocalDate.parse(it).isAfter(LocalDate.now()) }
    }

    private suspend fun hentAdresseKodeverk(
        postnummer: String?,
        landkode: String?,
        kommunenummer: String?
    ): AdresseKodeverk {
        return AdresseKodeverk(
            poststed = postnummer?.let { kodeverkConsumer.hentPostnummer().term(it) },
            land = landkode?.let { kodeverkConsumer.hentLandKoder().term(it) },
            kommune = hentKommuneKodeverksTerm(kommunenummer),
        )
    }

    private suspend fun hentLandKodeverksTerm(inbound: String?): String? {
        return inbound?.let { kodeverkConsumer.hentLandKoder().term(it) }
    }

    private suspend fun hentKommuneKodeverksTerm(inbound: String?): String? {
        return if ("0000" == inbound) {
            ""
        } else {
            inbound?.let { kodeverkConsumer.hentKommuner().term(it) }
        }
    }

    private suspend fun hentValutaKontoregisterKodeterm(kode: String?): String? {
        return kontoregisterConsumer.hentValutakoder().find { it.kode == kode }?.tekst
    }

    private suspend fun hentLandKontoregisterKodeterm(kode: String?): String? {
        return kontoregisterConsumer.hentLandkoder().find { it.kode == kode }?.tekst
    }

    companion object {
        private const val UKJENT_LAND = "XUK"
    }
}

