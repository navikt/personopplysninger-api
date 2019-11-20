package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.norg2.Norg2Consumer
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kontaktinfoConsumer: KontaktinfoConsumer,
        private var kodeverkConsumer: KodeverkConsumer,
        private var norg2Consumer: Norg2Consumer
) {

    private val kodeverkspraak = "nb"
    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val personaliaKodeverk = PersonaliaKodeverk()

        val inbound = personConsumer.hentPersonInfo(fodselsnr)

        personaliaKodeverk.kjonnterm = getKodeverksTerm(kodeverkConsumer.hentKjonn(), inbound.kjonn, "KJØNN")
        personaliaKodeverk.landterm = getKodeverksTerm(kodeverkConsumer.hentLandKoder(), inbound.foedtILand?.verdi, "LAND")
        personaliaKodeverk.utenlandskadresseterm= getKodeverksTerm(kodeverkConsumer.hentLandKoder(), inbound.adresseinfo?.utenlandskAdresse?.land, "LAND")
        personaliaKodeverk.foedekommuneterm = getKommuneKodeverksTerm(kodeverkConsumer.hentKommuner(), inbound.foedtIKommune?.verdi, "KOMMUNE")
        personaliaKodeverk.bostedskommuneterm = getKommuneKodeverksTerm(kodeverkConsumer.hentKommuner(), inbound.adresseinfo?.boadresse?.kommune, "KOMMUNE")
        personaliaKodeverk.bostedpostnummerterm = getKodeverksTerm(kodeverkConsumer.hentPostnummer(), inbound.adresseinfo?.boadresse?.postnummer, "POSTNUMMER")
        personaliaKodeverk.postnummerterm = getKodeverksTerm(kodeverkConsumer.hentPostnummer(), inbound.adresseinfo?.postadresse?.postnummer, "POSTNUMMER")
        personaliaKodeverk.tilleggsadressepostnummerterm = getKodeverksTerm(kodeverkConsumer.hentPostnummer(), inbound.adresseinfo?.tilleggsadresse?.postnummer, "POSTNUMMER")
        personaliaKodeverk.statusterm = getKodeverksTerm(kodeverkConsumer.hentPersonstatus(), inbound.status?.kode?.verdi, "PERSONSTATUS")
        personaliaKodeverk.sivilstandterm = getKodeverksTerm(kodeverkConsumer.hentSivilstand(), inbound.sivilstand?.kode?.verdi, "SIVILSTAND")
        personaliaKodeverk.spraakterm = getKodeverksTerm(kodeverkConsumer.hentSpraak(), inbound.spraak?.kode?.verdi, "SPRÅK")
        personaliaKodeverk.stasborgerskapterm = getKodeverksTerm(kodeverkConsumer.hentStatsborgerskap(), inbound.statsborgerskap?.kode?.verdi, "STATSBORGERSKAP")
        personaliaKodeverk.postadresselandterm = getKodeverksTerm(kodeverkConsumer.hentLandKoder(), inbound.adresseinfo?.postadresse?.land, "LAND")
        personaliaKodeverk.utenlandskbanklandterm = getKodeverksTerm(kodeverkConsumer.hentLandKoder(), inbound.utenlandskBank?.land?.verdi, "LAND")
        personaliaKodeverk.utenlandskbankvalutaterm = getKodeverksTerm(kodeverkConsumer.hentValuta(), inbound.utenlandskBank?.valuta?.verdi, "VALUTA")

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet = norg2Consumer.hentEnhet(tilknytning)
            personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
            personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
        }
        return personaliaOgAdresser
    }

    private fun getKodeverksTerm(kodeverk: GetKodeverkKoderBetydningerResponse, inbound: String?, type: String): String? {
        try {
            if (!inbound.isNullOrEmpty() && !kodeverk.betydninger.getValue(inbound).isEmpty()) {
                return kodeverk.betydninger.getValue(inbound)[0].beskrivelser.getValue(kodeverkspraak).term
            } else {
                return ""
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Oppslag på kodeverkstype ".plus(type).plus(" gav ingen treff for verdi ").plus(inbound))
        }
        return inbound
    }

    private fun getKommuneKodeverksTerm(kodeverk: GetKodeverkKoderBetydningerResponse, inbound: String?, type: String): String? {
        if ("0000".equals(inbound)) {
            return ""
        } else {
            return getKodeverksTerm(kodeverk, inbound, type)
        }
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        return KontaktinformasjonTransformer.toOutbound(inbound, fodselsnr)
    }

    fun hentEnhetKontaktinformasjon(enhetsnr: String?): GeografiskEnhetKontaktInformasjon {
        val inbound = norg2Consumer.hentKontaktinfo(enhetsnr)
        return GeografiskEnhetKontaktinformasjonTransformer.toOutbound(inbound)
    }

    fun hentGeografiskTilknytning(inbound: GeografiskTilknytning?): String? {
        return inbound?.bydel ?: inbound?.kommune
    }
}

