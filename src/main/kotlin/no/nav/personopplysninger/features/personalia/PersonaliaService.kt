package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.oppslag.norg2.Norg2Consumer
import no.nav.personopplysninger.oppslag.norg2.domain.Norg2Enhet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kontaktinfoConsumer: KontaktinfoConsumer,
        private var kodeverkConsumer: KodeverkConsumer,
        private var norg2Consumer: Norg2Consumer
) {
    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val personaliaKV = PersonaliaKodeverk()

        val inbound = personConsumer.hentPersonInfo(fodselsnr)

        personaliaKV.kjonnterm = kodeverkConsumer.hentKjonn().term(inbound.kjonn)
        personaliaKV.landterm = kodeverkConsumer.hentLandKoder().term(inbound.foedtILand?.verdi)
        personaliaKV.utenlandskadresseterm= kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.utenlandskAdresse?.land)
        personaliaKV.foedekommuneterm = getKommuneKodeverksTerm(inbound.foedtIKommune?.verdi)
        personaliaKV.bostedskommuneterm = getKommuneKodeverksTerm(inbound.adresseinfo?.boadresse?.kommune)
        personaliaKV.bostedpostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.boadresse?.postnummer)
        personaliaKV.postnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.postadresse?.postnummer)
        personaliaKV.tilleggsadressepostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.tilleggsadresse?.postnummer)
        personaliaKV.statusterm = kodeverkConsumer.hentPersonstatus().term(inbound.status?.kode?.verdi)
        personaliaKV.sivilstandterm = kodeverkConsumer.hentSivilstand().term(inbound.sivilstand?.kode?.verdi)
        personaliaKV.spraakterm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.kode?.verdi)
        personaliaKV.stasborgerskapterm = kodeverkConsumer.hentStatsborgerskap().term(inbound.statsborgerskap?.kode?.verdi)
        personaliaKV.postadresselandterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.postadresse?.land)
        personaliaKV.utenlandskbanklandterm = kodeverkConsumer.hentLandKoder().term(inbound.utenlandskBank?.land?.verdi)
        personaliaKV.utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inbound.utenlandskBank?.valuta?.verdi)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKV)
        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet: Norg2Enhet? = norg2Consumer.hentEnhet(tilknytning)
            if(enhet != null){
                personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
                personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
            }
        }
        return personaliaOgAdresser
    }

    private fun getKommuneKodeverksTerm(inbound: String?): String? {
        if ("0000".equals(inbound)) {
            return ""
        } else {
            return kodeverkConsumer.hentKommuner().term(inbound)
        }
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        return KontaktinformasjonTransformer.toOutbound(inbound, fodselsnr)
    }

    fun hentEnhetKontaktinformasjon(enhetsnr: String): GeografiskEnhetKontaktInformasjon {
        val inbound = norg2Consumer.hentKontaktinfo(enhetsnr)
        return GeografiskEnhetKontaktinformasjonTransformer.toOutbound(inbound)
    }

    fun hentGeografiskTilknytning(inbound: GeografiskTilknytning?): String? {
        return inbound?.bydel ?: inbound?.kommune
    }
}

