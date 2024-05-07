package no.nav.personopplysninger.personalia

import no.nav.personopplysninger.common.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.common.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.consumer.pdl.PdlService
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.common.consumer.pdl.dto.personalia.PdlStatsborgerskap
import no.nav.personopplysninger.personalia.consumer.Norg2Consumer
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.personalia.transformer.PersonaliaOgAdresserTransformer
import java.time.LocalDate

class PersonaliaService(
    private val kodeverkService: KodeverkService,
    private val norg2Consumer: Norg2Consumer,
    private val kontoregisterConsumer: KontoregisterConsumer,
    private val pdlService: PdlService
) {

    suspend fun hentPersoninfo(token: String, fodselsnr: String): PersonaliaOgAdresser {
        val pdlPersonInfo = pdlService.getPersonInfo(token, fodselsnr)

        val konto = kontoregisterConsumer.hentAktivKonto(token, fodselsnr)

        val geografiskTilknytning = pdlPersonInfo.geografiskTilknytning.let { it?.gtBydel ?: it?.gtKommune }

        val enhetKontaktInformasjon = geografiskTilknytning
            ?.let { norg2Consumer.hentEnhet(token, it) }
            ?.let { norg2Consumer.hentKontaktinfo(token, it.enhetNr) }

        val personaliaKV = createPersonaliaKodeverk(pdlPersonInfo, konto)

        return PersonaliaOgAdresserTransformer.toOutbound(pdlPersonInfo, konto, personaliaKV, enhetKontaktInformasjon)
    }

    private suspend fun createPersonaliaKodeverk(inboundPdl: PdlData, inboundKonto: Konto?): PersonaliaKodeverk {
        val pdlPerson = inboundPdl.person
        val pdlGeografiskTilknytning = inboundPdl.geografiskTilknytning

        val kontaktadresse = pdlPerson.kontaktadresse
        val bostedsadresse = pdlPerson.bostedsadresse.firstOrNull()
        val deltBosted = pdlPerson.deltBosted.firstOrNull()
        val oppholdsadresse = pdlPerson.oppholdsadresse

        return PersonaliaKodeverk().apply {
            foedekommuneterm = getKommuneKodeverksTerm(pdlPerson.foedsel.firstOrNull()?.foedekommune)
            foedelandterm = kodeverkService.hentLandKoder().term(pdlPerson.foedsel.firstOrNull()?.foedeland)
            gtLandterm = kodeverkService.hentLandKoder().term(pdlGeografiskTilknytning?.gtLand)
            statsborgerskaptermer = hentGyldigeStatsborgerskap(pdlPerson.statsborgerskap)
            utenlandskbanklandterm =
                if (inboundKonto?.utenlandskKontoInfo != null) hentLandKodeterm(inboundKonto.utenlandskKontoInfo.bankLandkode) else null
            utenlandskbankvalutaterm =
                if (inboundKonto?.utenlandskKontoInfo != null) hentValutaKodeterm(inboundKonto.utenlandskKontoInfo.valutakode) else null
            kontaktadresseKodeverk =
                kontaktadresse.map { hentAdresseKodeverk(it.postnummer, it.landkode, it.kommunenummer) }
            bostedsadresseKodeverk =
                hentAdresseKodeverk(bostedsadresse?.postnummer, bostedsadresse?.landkode, bostedsadresse?.kommunenummer)
            deltBostedKodeverk =
                hentAdresseKodeverk(deltBosted?.postnummer, deltBosted?.landkode, deltBosted?.kommunenummer)
            oppholdsadresseKodeverk =
                oppholdsadresse.map { hentAdresseKodeverk(it.postnummer, it.landkode, it.kommunenummer) }
        }
    }

    private suspend fun hentGyldigeStatsborgerskap(statsborgerskap: List<PdlStatsborgerskap>): List<String> {
        return statsborgerskap
            .filter { it.land != "XUK" && it.gyldigTilOgMed?.isBefore(LocalDate.now()) != true } // Filtrer ut ukjent og ugyldige
            .map { kodeverkService.hentStatsborgerskap().term(it.land) }
            .filter { it.isNotEmpty() }
    }

    private suspend fun hentAdresseKodeverk(
        postnummer: String?,
        landkode: String?,
        kommunenummer: String?
    ): AdresseKodeverk {
        return AdresseKodeverk().apply {
            poststed = kodeverkService.hentPostnummer().term(postnummer)
            land = kodeverkService.hentLandKoder().term(landkode)
            kommune = getKommuneKodeverksTerm(kommunenummer)
        }
    }

    private suspend fun getKommuneKodeverksTerm(inbound: String?): String {
        return if ("0000" == inbound) {
            ""
        } else {
            kodeverkService.hentKommuner().term(inbound)
        }
    }

    private suspend fun hentValutaKodeterm(kode: String?): String? {
        return kontoregisterConsumer.hentValutakoder().find { valuta -> kode == valuta.kode }?.tekst
    }

    private suspend fun hentLandKodeterm(kode: String?): String? {
        return kontoregisterConsumer.hentLandkoder().find { land -> kode == land.kode }?.tekst
    }
}

