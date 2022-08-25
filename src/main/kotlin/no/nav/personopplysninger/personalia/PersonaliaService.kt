package no.nav.personopplysninger.personalia

import no.nav.personopplysninger.common.kodeverk.KodeverkService
import no.nav.personopplysninger.common.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.common.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.pdl.PdlService
import no.nav.personopplysninger.common.pdl.dto.PdlData
import no.nav.personopplysninger.common.pdl.dto.personalia.PdlStatsborgerskap
import no.nav.personopplysninger.personalia.consumer.Norg2Consumer
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.personalia.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.personalia.transformer.PersonaliaOgAdresserTransformer
import java.time.LocalDate

class PersonaliaService(
    private var kodeverkService: KodeverkService,
    private var norg2Consumer: Norg2Consumer,
    private var kontoregisterConsumer: KontoregisterConsumer,
    private var pdlService: PdlService
) {

    suspend fun hentPersoninfo(token: String, fodselsnr: String): PersonaliaOgAdresser {

        val pdlPersonInfo = pdlService.getPersonInfo(token, fodselsnr)

        val konto = kontoregisterConsumer.hentAktivKonto(token, fodselsnr)

        val personaliaKV = createPersonaliaKodeverk(pdlPersonInfo, konto)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(pdlPersonInfo, konto, personaliaKV)

        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet = norg2Consumer.hentEnhet(token, tilknytning)
            if (enhet != null) {
                personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
                personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(token, enhet.enhetNr)
            }
        }
        return personaliaOgAdresser
    }

    private suspend fun createPersonaliaKodeverk(inboundPdl: PdlData, inboundKonto: Konto?): PersonaliaKodeverk {
        val pdlPerson = inboundPdl.person!!
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
                kodeverkService.hentLandKoderISO2().term(inboundKonto?.utenlandskKontoInfo?.bankLandkode)
            utenlandskbankvalutaterm = kodeverkService.hentValuta().term(inboundKonto?.utenlandskKontoInfo?.valutakode)
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

    private suspend fun hentEnhetKontaktinformasjon(
        token: String,
        enhetsnr: String
    ): GeografiskEnhetKontaktInformasjon {
        val inbound = norg2Consumer.hentKontaktinfo(token, enhetsnr)
        return GeografiskEnhetKontaktinformasjonTransformer.toOutbound(inbound)
    }

    private fun hentGeografiskTilknytning(inbound: GeografiskTilknytning?): String? {
        return inbound?.bydel ?: inbound?.kommune
    }
}

