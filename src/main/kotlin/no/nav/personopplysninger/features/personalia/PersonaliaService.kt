package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.features.norg2.Norg2Consumer
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.GeografiskEnhetKontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Personinfo
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
    private val nullstring = "0000"
    private var personaliaKodeverk = PersonaliaKodeverk()
    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)

    fun hentRetningsnummer(): Array<RetningsnummerDTO> {
        return kodeverkConsumer.hentRetningsnummer().betydninger
                .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.values.first().tekst) }
                .toTypedArray()
    }

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val inbound = personConsumer.hentPersonInfo(fodselsnr)
        val kjonn = kodeverkConsumer.hentKjonn(inbound.kjonn)
        val foedtkommune = kodeverkConsumer.hentKommuner(inbound.foedtIKommune?.verdi)
        val bostedskommune = kodeverkConsumer.hentKommuner(inbound.adresseinfo?.boadresse?.kommune)
        val land = kodeverkConsumer.hentLandKoder((inbound.foedtILand?.verdi))
        val status = kodeverkConsumer.hentPersonstatus(inbound.status?.kode?.verdi)
        val postnummer = kodeverkConsumer.hentPostnummer(inbound.adresseinfo?.postadresse?.postnummer)
        val postbostedsnummer = kodeverkConsumer.hentPostnummer(inbound.adresseinfo?.boadresse?.postnummer)
        val posttilleggsnummer = kodeverkConsumer.hentPostnummer(inbound.adresseinfo?.tilleggsadresse?.postnummer)
        val sivilstand = kodeverkConsumer.hentSivilstand(inbound.sivilstand?.kode?.verdi)
        val spraak = kodeverkConsumer.hentSpraak(inbound.spraak?.kode?.verdi)
        val statsborgerskap = kodeverkConsumer.hentStatsborgerskap(inbound.statsborgerskap?.kode?.verdi)
        val valuta = kodeverkConsumer.hentValuta(inbound.utenlandskBank?.valuta?.verdi)

        getTerms(kjonn, land, foedtkommune, bostedskommune, postbostedsnummer, postnummer, posttilleggsnummer, status, sivilstand, spraak, statsborgerskap, valuta, inbound)

        val personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)
        if (tilknytning != null) {
            val enhet = norg2Consumer.hentEnhet(tilknytning)
            personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.navn
            personaliaOgAdresser.enhetKontaktInformasjon.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
        }
        return personaliaOgAdresser
    }

    private fun getTerms(kjonn: GetKodeverkKoderBetydningerResponse, land: GetKodeverkKoderBetydningerResponse, foedtkommune: GetKodeverkKoderBetydningerResponse, bostedskommune: GetKodeverkKoderBetydningerResponse, postbostedsnummer: GetKodeverkKoderBetydningerResponse, postnummer: GetKodeverkKoderBetydningerResponse, posttilleggsnummer: GetKodeverkKoderBetydningerResponse, status: GetKodeverkKoderBetydningerResponse, sivilstand: GetKodeverkKoderBetydningerResponse, spraak: GetKodeverkKoderBetydningerResponse, statsborgerskap: GetKodeverkKoderBetydningerResponse, valuta: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {

        getKjonnTerm(kjonn, inbound.kjonn)
        getLandTerm(land, inbound.foedtILand?.verdi)
        getUtenlandskAdresseTerm(land, inbound.adresseinfo?.utenlandskAdresse?.land)
        getKommuneTerm(foedtkommune, inbound.foedtIKommune?.verdi)
        getBostedskommuneTerm(bostedskommune, inbound.adresseinfo?.boadresse?.kommune)
        getBostedpostnummerTerm(postbostedsnummer, inbound.adresseinfo?.boadresse?.postnummer)
        getPostadressePostnummerTerm(postnummer, inbound.adresseinfo?.postadresse?.postnummer)
        getTilleggsadresseTerm(posttilleggsnummer, inbound.adresseinfo?.tilleggsadresse?.postnummer)
        getStatusTerm(status, inbound.status?.kode?.verdi)
        getSivilstandTerm(sivilstand, inbound.sivilstand?.kode?.verdi)
        getSpraakTerm(spraak, inbound.spraak?.kode?.verdi)
        getStatsborgerskapTerm(statsborgerskap, inbound.statsborgerskap?.kode?.verdi)
        getPostadresseLandTerm(land, inbound.adresseinfo?.postadresse?.land)
        getUtenlandskBankLandTerm(land, inbound.utenlandskBank?.land?.verdi)
        getUtenlandskBankValutaTerm(valuta, inbound.utenlandskBank?.valuta?.verdi)
    }

    private fun getPostadressePostnummerTerm(postnummer: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !postnummer.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.postnummerterm = postnummer.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.postnummerterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.postnummerterm = inbound
            log.warn("Element not found in Postadressepostnummer: " + inbound)
        }

    }

    private fun getBostedpostnummerTerm(postbostedsnummer: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !postbostedsnummer.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.bostedpostnummerterm = postbostedsnummer.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.bostedpostnummerterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.bostedpostnummerterm = inbound
            log.warn("Element not found in Boadressepostnummer: " + inbound)
        }

    }

    private fun getBostedskommuneTerm(bostedskommune: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !bostedskommune.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.bostedskommuneterm = bostedskommune.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.bostedskommuneterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.bostedskommuneterm = inbound
            log.warn("Element not found in Bostedskommune: " + inbound)
        }

    }

    private fun getTilleggsadresseTerm(posttilleggsnummer: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !posttilleggsnummer.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.tilleggsadressepostnummerterm = posttilleggsnummer.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.tilleggsadressepostnummerterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.tilleggsadressepostnummerterm = inbound
            log.warn("Element not found in Tilleggsadresse: " + inbound)
        }

    }

    private fun getStatusTerm(status: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !status.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.statusterm = status.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.statusterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.statusterm = inbound
            log.warn("Element not found in Status: " + inbound)
        }

    }

    private fun getSivilstandTerm(sivilstand: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !sivilstand.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.sivilstandterm = sivilstand.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.sivilstandterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.sivilstandterm = inbound
            log.warn("Element not found in Adressepostnummer: " + inbound)
        }

    }

    private fun getSpraakTerm(spraak: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !spraak.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.spraakterm = spraak.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.spraakterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.spraakterm = inbound
            log.warn("Element not found in Spraak: " + inbound)
        }
    }

    private fun getStatsborgerskapTerm(statsborgerskap: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !statsborgerskap.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.stasborgerskapterm = statsborgerskap.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.stasborgerskapterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.stasborgerskapterm = inbound
            log.warn("Element not found in Statsborgerskap: " + inbound)
        }
    }

    private fun getKommuneTerm(foedtkommune: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !nullstring.equals(inbound) && !foedtkommune.betydninger.getValue(inbound).isEmpty()) {
                personaliaKodeverk.foedekommuneterm = foedtkommune.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.foedekommuneterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.foedekommuneterm = inbound
            log.warn("Element not found in Kommune: " + inbound)
        }

    }

    private fun getLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !(land.betydninger.getValue(inbound).isEmpty())) {
                personaliaKodeverk.landterm = land.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.landterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.landterm = inbound
            log.warn("Element not found in Land: " + inbound)
        }

    }


    private fun getUtenlandskAdresseTerm(land: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty()) {
                personaliaKodeverk.utenlandskadresseterm = land.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.utenlandskadresseterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskadresseterm = inbound
            log.warn("Element not found in Utenlandskadresse: " + inbound)
        }

    }

    private fun getKjonnTerm(kjonn: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !(kjonn.betydninger.getValue(inbound).isEmpty())) {
                personaliaKodeverk.kjonnterm = kjonn.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.kjonnterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.kjonnterm = inbound
            log.warn("Element not found in Kjonn: " + inbound)
        }

    }

    private fun getPostadresseLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty()) {
                personaliaKodeverk.postadresselandterm = land.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.postadresselandterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.landterm = inbound
            log.warn("Element not found in Land: " + inbound)
        }
    }


    private fun getUtenlandskBankLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !(land.betydninger.getValue(inbound).isEmpty())) {
                personaliaKodeverk.utenlandskbanklandterm = land.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.utenlandskbanklandterm = ""
            }

        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskbanklandterm = inbound
            log.warn("Element not found in Land (Utenlandsk bank): " + inbound)
        }

    }


    private fun getUtenlandskBankValutaTerm(valuta: GetKodeverkKoderBetydningerResponse, inbound: String?) {
        try {
            if (!inbound.isNullOrEmpty() && !(valuta.betydninger.getValue(inbound).isEmpty())) {
                personaliaKodeverk.utenlandskbankvalutaterm = valuta.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            } else {
                personaliaKodeverk.utenlandskbankvalutaterm = ""
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskbankvalutaterm = inbound
            log.warn("Element not found in Valuta: " + inbound)
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
        log.warn("Bydel " + inbound?.bydel)
        if (inbound?.bydel != null) {
            return inbound.bydel
        } else {
            return inbound?.kommune
        }
    }
}

