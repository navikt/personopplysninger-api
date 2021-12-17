package no.nav.personopplysninger.features.personaliagammel

import no.nav.personopplysninger.features.personaliagammel.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personaliagammel.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personaliagammel.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personaliagammel.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personaliagammel.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personaliagammel.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personaliagammel.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personaliagammel.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personaliagammel.pdl.PdlServiceGammel
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse.util.landkode
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse.util.postnummer
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.oppslag.norg2.Norg2Consumer
import no.nav.tps.person.Personinfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaServiceGammel @Autowired constructor(
    private var personConsumer: PersonConsumerGammel,
    private var kontaktinfoConsumer: KontaktinfoConsumerGammel,
    private var kodeverkConsumer: KodeverkConsumer,
    private var norg2Consumer: Norg2Consumer,
    private var pdlService: PdlServiceGammel
) {
    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {

        val inbound = personConsumer.hentPersonInfo(fodselsnr)

        val pdlPersonInfo = pdlService.getPersonInfo(fodselsnr)

        val personaliaKV = createPersonaliaKodeverk(inbound, pdlPersonInfo)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlPersonInfo, personaliaKV)

        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet = norg2Consumer.hentEnhetDersomGyldig(tilknytning)
            if(enhet != null){
                personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
                personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
            }
        }
        return personaliaOgAdresser
    }

    private fun createPersonaliaKodeverk(inbound: Personinfo, inboundPdl: PdlPersonInfo): PersonaliaKodeverk {
        val kontaktadresse = inboundPdl.kontaktadresse.firstOrNull()

        return PersonaliaKodeverk().apply {
            kjonnterm = kodeverkConsumer.hentKjonn().term(inbound.kjonn)
            landterm = kodeverkConsumer.hentLandKoder().term(inbound.foedtILand?.verdi)
            utenlandskadresseterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.utenlandskAdresse?.land)
            foedekommuneterm = getKommuneKodeverksTerm(inbound.foedtIKommune?.verdi)
            bostedskommuneterm = getKommuneKodeverksTerm(inbound.adresseinfo?.boadresse?.kommune)
            bostedpostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.boadresse?.postnummer)
            postnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.postadresse?.postnummer)
            tilleggsadressepostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.tilleggsadresse?.postnummer)
            statusterm = kodeverkConsumer.hentPersonstatus().term(inbound.status?.kode?.verdi)
            sivilstandterm = kodeverkConsumer.hentSivilstand().term(inbound.sivilstand?.kode?.verdi)
            spraakterm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.kode?.verdi)
            stasborgerskapterm = kodeverkConsumer.hentStatsborgerskap().term(inbound.statsborgerskap?.kode?.verdi)
            postadresselandterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.postadresse?.land)
            utenlandskbanklandterm = kodeverkConsumer.hentLandKoder().term(inbound.utenlandskBank?.land?.verdi)
            utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inbound.utenlandskBank?.valuta?.verdi)
            kontaktadressePostSted = kodeverkConsumer.hentPostnummer().term(kontaktadresse?.postnummer)
            kontaktadresseLand = kodeverkConsumer.hentLandKoder().term(kontaktadresse?.landkode)
        }
    }

    private fun getKommuneKodeverksTerm(inbound: String?): String? {
        return if ("0000" == inbound) {
            ""
        } else {
            kodeverkConsumer.hentKommuner().term(inbound)
        }
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        return KontaktinformasjonTransformer.toOutbound(inbound, fodselsnr)
    }

    private fun hentEnhetKontaktinformasjon(enhetsnr: String): GeografiskEnhetKontaktInformasjon {
        val inbound = norg2Consumer.hentKontaktinfo(enhetsnr)
        return GeografiskEnhetKontaktinformasjonTransformer.toOutbound(inbound)
    }

    private fun hentGeografiskTilknytning(inbound: GeografiskTilknytning?): String? {
        return inbound?.bydel ?: inbound?.kommune
    }
}

