package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.*
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.PdlService
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.oppslag.norg2.Norg2Consumer
import no.nav.tps.person.Personinfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kontaktinfoConsumer: KontaktinfoConsumer,
        private var kodeverkConsumer: KodeverkConsumer,
        private var norg2Consumer: Norg2Consumer,
        private var pdlService: PdlService
) {
    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {

        val inbound = personConsumer.hentPersonInfo(fodselsnr)

        val pdlPersonInfo = pdlService.getPersonInfo(fodselsnr)

        val personaliaKV = createPersonaliaKodeverk(inbound, pdlPersonInfo.person!!)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlPersonInfo.person, personaliaKV)

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

    fun hentPersoninfoMigrert(fodselsnr: String): PersonaliaOgAdresserMigrert {

        val inbound = personConsumer.hentPersonInfo(fodselsnr)

        val pdlPersonInfo = pdlService.getPersonInfo(fodselsnr)

        val personaliaKV = createPersonaliaKodeverkMigrert(inbound, pdlPersonInfo)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutboundMigrert(inbound, pdlPersonInfo, personaliaKV)

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

    private fun createPersonaliaKodeverk(inbound: Personinfo, inboundPdl: PdlPerson): PersonaliaKodeverk {
        val kontaktadresse = inboundPdl.kontaktadresse.firstOrNull()
        val bostedsadresse = inboundPdl.bostedsadresse.firstOrNull()
        val deltBosted = inboundPdl.deltBosted.firstOrNull()
        val oppholdsadresse = inboundPdl.oppholdsadresse.firstOrNull()

        return PersonaliaKodeverk().apply {
            kjonnterm = kodeverkConsumer.hentKjonn().term(inbound.kjonn)
            landterm = kodeverkConsumer.hentLandKoder().term(inbound.foedtILand?.verdi)
            utenlandskadresseterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.utenlandskAdresse?.land)
            foedekommuneterm = getKommuneKodeverksTerm(inbound.foedtIKommune?.verdi)
            bostedskommuneterm = getKommuneKodeverksTerm(inbound.adresseinfo?.boadresse?.kommune)
            bostedpostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.boadresse?.postnummer)
            postnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.postadresse?.postnummer)
            tilleggsadressepostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.tilleggsadresse?.postnummer)
            sivilstandterm = kodeverkConsumer.hentSivilstand().term(inbound.sivilstand?.kode?.verdi)
            spraakterm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.kode?.verdi)
            stasborgerskapterm = kodeverkConsumer.hentStatsborgerskap().term(inbound.statsborgerskap?.kode?.verdi)
            postadresselandterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.postadresse?.land)
            utenlandskbanklandterm = kodeverkConsumer.hentLandKoder().term(inbound.utenlandskBank?.land?.verdi)
            utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inbound.utenlandskBank?.valuta?.verdi)
            kontaktadressePostSted = kodeverkConsumer.hentPostnummer().term(kontaktadresse?.postnummer)
            kontaktadresseLand = kodeverkConsumer.hentLandKoder().term(kontaktadresse?.landkode)
            bostedsadressePostSted = kodeverkConsumer.hentPostnummer().term(bostedsadresse?.postnummer)
            bostedsadresseLand = kodeverkConsumer.hentLandKoder().term(bostedsadresse?.landkode)
            deltBostedPostSted = kodeverkConsumer.hentPostnummer().term(deltBosted?.postnummer)
            deltBostedLand = kodeverkConsumer.hentLandKoder().term(deltBosted?.landkode)
            oppholdsadressePostSted = kodeverkConsumer.hentPostnummer().term(oppholdsadresse?.postnummer)
            oppholdsadresseLand = kodeverkConsumer.hentLandKoder().term(oppholdsadresse?.landkode)
        }
    }

    private fun createPersonaliaKodeverkMigrert(inbound: Personinfo, inboundPdl: PdlData): PersonaliaKodeverk {
        val pdlPerson = inboundPdl.person!!
        val pdlGeografiskTilknytning = inboundPdl.geografiskTilknytning!!

        val kontaktadresse = pdlPerson.kontaktadresse.firstOrNull()

        return PersonaliaKodeverk().apply {
            landterm = kodeverkConsumer.hentLandKoder().term(pdlPerson.foedsel.firstOrNull()?.foedeland)
            gtLandterm = kodeverkConsumer.hentLandKoder().term(pdlGeografiskTilknytning.gtLand)
            utenlandskadresseterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.utenlandskAdresse?.land)
            foedekommuneterm = getKommuneKodeverksTerm(pdlPerson.foedsel.firstOrNull()?.foedekommune)
            bostedskommuneterm = getKommuneKodeverksTerm(inbound.adresseinfo?.boadresse?.kommune)
            bostedpostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.boadresse?.postnummer)
            postnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.postadresse?.postnummer)
            tilleggsadressepostnummerterm = kodeverkConsumer.hentPostnummer().term(inbound.adresseinfo?.tilleggsadresse?.postnummer)
            spraakterm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.kode?.verdi)
            stasborgerskapterm = kodeverkConsumer.hentStatsborgerskap().term(pdlPerson.statsborgerskap.firstOrNull()?.land)
            postadresselandterm = kodeverkConsumer.hentLandKoder().term(inbound.adresseinfo?.postadresse?.land)
            utenlandskbanklandterm = kodeverkConsumer.hentLandKoder().term(inbound.utenlandskBank?.land?.verdi)
            utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inbound.utenlandskBank?.valuta?.verdi)
            kontaktadressePostSted = kodeverkConsumer.hentPostnummer().term(kontaktadresse?.postnummer)
            kontaktadresseLand = kodeverkConsumer.hentLandKoder().term(kontaktadresse?.landkode)
        }
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

