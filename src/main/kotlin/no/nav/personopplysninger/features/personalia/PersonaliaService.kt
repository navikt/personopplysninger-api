package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.AdresseKodeverk
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.PdlService
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
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

        val personaliaKV = createPersonaliaKodeverk(inbound, pdlPersonInfo)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlPersonInfo, personaliaKV)

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

    private fun createPersonaliaKodeverk(inbound: Personinfo, inboundPdl: PdlData): PersonaliaKodeverk {
        val pdlPerson = inboundPdl.person!!
        val pdlGeografiskTilknytning = inboundPdl.geografiskTilknytning!! // todo: fiks mulig NPE

        val kontaktadresse = pdlPerson.kontaktadresse
        val bostedsadresse = pdlPerson.bostedsadresse.firstOrNull()
        val deltBosted = pdlPerson.deltBosted.firstOrNull()
        val oppholdsadresse = pdlPerson.oppholdsadresse

        return PersonaliaKodeverk().apply {
            foedekommuneterm = getKommuneKodeverksTerm(pdlPerson.foedsel.firstOrNull()?.foedekommune)
            foedelandterm = kodeverkConsumer.hentLandKoder().term(pdlPerson.foedsel.firstOrNull()?.foedeland)
            gtLandterm = kodeverkConsumer.hentLandKoder().term(pdlGeografiskTilknytning.gtLand)
            statsborgerskapterm =
                kodeverkConsumer.hentStatsborgerskap().term(pdlPerson.statsborgerskap.firstOrNull()?.land)
            utenlandskbanklandterm = kodeverkConsumer.hentLandKoder().term(inbound.utenlandskBank?.land?.verdi)
            utenlandskbankvalutaterm = kodeverkConsumer.hentValuta().term(inbound.utenlandskBank?.valuta?.verdi)
            kontaktadresseKodeverk = kontaktadresse.map { adresse ->
                hentAdresseKodeverk(
                    adresse.postnummer,
                    adresse.landkode,
                    adresse.kommunenummer
                )
            }
            bostedsadresseKodeverk =
                hentAdresseKodeverk(bostedsadresse?.postnummer, bostedsadresse?.landkode, bostedsadresse?.kommunenummer)
            deltBostedKodeverk =
                hentAdresseKodeverk(deltBosted?.postnummer, deltBosted?.landkode, deltBosted?.kommunenummer)
            oppholdsadresseKodeverk = oppholdsadresse.map { adresse ->
                hentAdresseKodeverk(
                    adresse.postnummer,
                    adresse.landkode,
                    adresse.kommunenummer
                )
            }
        }
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
        val spraakTerm = kodeverkConsumer.hentSpraak().term(inbound.spraak.uppercase())
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

