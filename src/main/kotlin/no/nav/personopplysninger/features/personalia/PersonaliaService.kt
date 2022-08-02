package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kodeverk.domain.AdresseKodeverk
import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.kontaktinformasjon.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.domain.Konto
import no.nav.personopplysninger.consumer.norg2.Norg2Consumer
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlStatsborgerskap
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils.isEmpty
import java.time.LocalDate

@Service
class PersonaliaService @Autowired constructor(
    private var kontaktinfoConsumer: KontaktinfoConsumer,
    private var kodeverkConsumer: KodeverkConsumer,
    private var norg2Consumer: Norg2Consumer,
    private var kontoregisterConsumer: KontoregisterConsumer,
    private var pdlService: PdlService
) {

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {

        val pdlPersonInfo = pdlService.getPersonInfo(fodselsnr)

        val konto = kontoregisterConsumer.hentAktivKonto(fodselsnr)

        val personaliaKV = createPersonaliaKodeverk(pdlPersonInfo, konto)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(pdlPersonInfo, konto, personaliaKV)

        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet = norg2Consumer.hentEnhetDersomGyldig(tilknytning)
            if (enhet != null) {
                personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
                personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
            }
        }
        return personaliaOgAdresser
    }

    private fun createPersonaliaKodeverk(inboundPdl: PdlData, inboundKonto: Konto?): PersonaliaKodeverk {
        val pdlPerson = inboundPdl.person!!
        val pdlGeografiskTilknytning = inboundPdl.geografiskTilknytning

        val kontaktadresse = pdlPerson.kontaktadresse
        val bostedsadresse = pdlPerson.bostedsadresse.firstOrNull()
        val deltBosted = pdlPerson.deltBosted.firstOrNull()
        val oppholdsadresse = pdlPerson.oppholdsadresse

        return PersonaliaKodeverk().apply {
            foedekommuneterm = getKommuneKodeverksTerm(pdlPerson.foedsel.firstOrNull()?.foedekommune)
            foedelandterm = kodeverkConsumer.hentLandKoder().term(pdlPerson.foedsel.firstOrNull()?.foedeland)
            gtLandterm = kodeverkConsumer.hentLandKoder().term(pdlGeografiskTilknytning?.gtLand)
            statsborgerskaptermer = hentGyldigeStatsborgerskap(pdlPerson.statsborgerskap)
            utenlandskbanklandterm = kodeverkConsumer.hentLandKoderISO2().term(inboundKonto?.utenlandskKontoInfo?.bankLandkode)
            utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inboundKonto?.utenlandskKontoInfo?.valutakode)
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

    private fun hentGyldigeStatsborgerskap(statsborgerskap: List<PdlStatsborgerskap>): List<String> {
        return statsborgerskap
            .filter { it.land != "XUK" && it.gyldigTilOgMed?.isBefore(LocalDate.now()) != true } // Filtrer ut ukjent og ugyldige
            .map { kodeverkConsumer.hentStatsborgerskap().term(it.land) }
            .filter { !isEmpty(it) }
    }

    private fun hentAdresseKodeverk(postnummer: String?, landkode: String?, kommunenummer: String?): AdresseKodeverk {
        return AdresseKodeverk().apply {
            poststed = kodeverkConsumer.hentPostnummer().term(postnummer)
            land = kodeverkConsumer.hentLandKoder().term(landkode)
            kommune = getKommuneKodeverksTerm(kommunenummer)
        }
    }

    private fun getKommuneKodeverksTerm(inbound: String?): String {
        return if ("0000" == inbound) {
            ""
        } else {
            kodeverkConsumer.hentKommuner().term(inbound)
        }
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        val spraakTerm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.uppercase())
        return KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)
    }

    private fun hentEnhetKontaktinformasjon(enhetsnr: String): GeografiskEnhetKontaktInformasjon {
        val inbound = norg2Consumer.hentKontaktinfo(enhetsnr)
        return GeografiskEnhetKontaktinformasjonTransformer.toOutbound(inbound)
    }

    private fun hentGeografiskTilknytning(inbound: GeografiskTilknytning?): String? {
        return inbound?.bydel ?: inbound?.kommune
    }
}

