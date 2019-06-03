package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
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

        var personaliaOgAdresser = PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
        val tilknytning = hentGeografiskTilknytning(personaliaOgAdresser.adresser?.geografiskTilknytning)

        getTerms(kjonn, land, foedtkommune, bostedskommune, postbostedsnummer, postnummer, posttilleggsnummer, status, sivilstand, spraak, statsborgerskap, valuta, inbound)

        val enhet = norg2Consumer.hentEnhet(tilknytning)

        personaliaOgAdresser.adresser?.geografiskTilknytning?.enhet = enhet.enhetNr
        personaliaOgAdresser?.enhetKontaktInformasjon?.enhet = hentEnhetKontaktinformasjon(enhet.enhetNr)
        return personaliaOgAdresser
    }

    private fun getTerms(kjonn: GetKodeverkKoderBetydningerResponse, land: GetKodeverkKoderBetydningerResponse, foedtkommune: GetKodeverkKoderBetydningerResponse, bostedskommune: GetKodeverkKoderBetydningerResponse, postbostedsnummer: GetKodeverkKoderBetydningerResponse, postnummer: GetKodeverkKoderBetydningerResponse, posttilleggsnummer: GetKodeverkKoderBetydningerResponse, status: GetKodeverkKoderBetydningerResponse, sivilstand: GetKodeverkKoderBetydningerResponse, spraak: GetKodeverkKoderBetydningerResponse, statsborgerskap: GetKodeverkKoderBetydningerResponse, valuta: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        getKjonnTerm(kjonn, inbound)
        getLandTerm(land, inbound)
        getUtenlandskAdresseTerm(land, inbound)
        getKommuneTerm(foedtkommune, inbound)
        getBostedskommuneTerm(bostedskommune, inbound)
        getBostedpostnummerTerm(postbostedsnummer, inbound)
        getPostadressePostnummerTerm(postnummer, inbound)
        getTilleggsadresseTerm(posttilleggsnummer, inbound)
        getStatusTerm(status, inbound)
        getSivilstandTerm(sivilstand, inbound)
        getSpraakTerm(spraak, inbound)
        getStatsborgerskapTerm(statsborgerskap, inbound)
        getPostadresseLandTerm(land, inbound)
        getUtenlandskBankLandTerm(land, inbound)
        getUtenlandskBankValutaTerm(valuta, inbound)
    }

    private fun getPostadressePostnummerTerm(postnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.postadresse?.postnummer.isNullOrEmpty() && !postnummer.betydninger.getValue(inbound.adresseinfo?.postadresse?.postnummer).isEmpty()) {
                personaliaKodeverk.postnummerterm = postnummer.betydninger.getValue(inbound.adresseinfo?.postadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.postnummerterm = inbound.adresseinfo?.postadresse?.postnummer
            log.warn("Element not found in Postadressepostnummer: " + inbound.adresseinfo?.postadresse?.postnummer)
        }

    }

    private fun getBostedpostnummerTerm(postbostedsnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.boadresse?.postnummer.isNullOrEmpty() && !postbostedsnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer).isEmpty()) {
                personaliaKodeverk.bostedpostnummerterm = postbostedsnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.bostedpostnummerterm = inbound.adresseinfo?.boadresse?.postnummer
            log.warn("Element not found in Boadressepostnummer: " + inbound.adresseinfo?.boadresse?.postnummer)
        }

    }

    private fun getBostedskommuneTerm(bostedskommune: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.boadresse?.kommune.isNullOrEmpty() && !bostedskommune.betydninger.getValue(inbound.adresseinfo?.boadresse?.kommune).isEmpty()) {
                personaliaKodeverk.bostedskommuneterm = bostedskommune.betydninger.getValue(inbound.adresseinfo?.boadresse?.kommune)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.bostedskommuneterm = inbound.adresseinfo?.boadresse?.kommune
            log.warn("Element not found in Bostedskommune: " + inbound.adresseinfo?.boadresse?.kommune)
        }

    }

    private fun getTilleggsadresseTerm(posttilleggsnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.tilleggsadresse?.postnummer.isNullOrEmpty() && !posttilleggsnummer.betydninger.getValue(inbound.adresseinfo?.tilleggsadresse?.postnummer).isEmpty()) {
                personaliaKodeverk.tilleggsadressepostnummerterm = posttilleggsnummer.betydninger.getValue(inbound.adresseinfo?.tilleggsadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.tilleggsadressepostnummerterm = inbound.adresseinfo?.tilleggsadresse?.postnummer
            log.warn("Element not found in Tilleggsadresse: " + inbound.adresseinfo?.tilleggsadresse?.postnummer)
        }

    }

    private fun getStatusTerm(status: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.status?.kode?.verdi.isNullOrEmpty() && !status.betydninger.getValue(inbound.status?.kode?.verdi).isEmpty()) {
                personaliaKodeverk.statusterm = status.betydninger.getValue(inbound.status?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.statusterm = inbound.status?.kode?.verdi
            log.warn("Element not found in Status: " + inbound.status?.kode?.verdi)
        }

    }

    private fun getSivilstandTerm(sivilstand: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.sivilstand?.kode?.verdi.isNullOrEmpty() && !sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi).isEmpty()) {
                personaliaKodeverk.sivilstandterm = sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.sivilstandterm = inbound.adresseinfo?.postadresse?.postnummer
            log.warn("Element not found in Adressepostnummer: " + inbound.adresseinfo?.postadresse?.postnummer)
        }

    }

    private fun getSpraakTerm(spraak: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.spraak?.kode?.verdi.isNullOrEmpty() && !spraak?.betydninger!!.getValue(inbound.spraak?.kode?.verdi).isEmpty()) {
                personaliaKodeverk.spraakterm = spraak?.betydninger!!.getValue(inbound.spraak?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.spraakterm = inbound.spraak?.kode?.verdi
            log.warn("Element not found in Spraak: " + inbound.spraak?.kode?.verdi)
        }
    }

    private fun getStatsborgerskapTerm(statsborgerskap: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.statsborgerskap?.kode?.verdi.isNullOrEmpty() && !statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi).isEmpty()) {
                personaliaKodeverk.stasborgerskapterm = statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.stasborgerskapterm = inbound.statsborgerskap?.kode?.verdi
            log.warn("Element not found in Statsborgerskap: " + inbound.statsborgerskap?.kode?.verdi)
        }
    }

    private fun getKommuneTerm(foedtkommune: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.foedtIKommune?.verdi.isNullOrEmpty() && !nullstring.equals(inbound.foedtIKommune?.verdi) && !foedtkommune.betydninger.getValue(inbound.foedtIKommune?.verdi).isEmpty()) {
                personaliaKodeverk.foedekommuneterm = foedtkommune.betydninger.getValue(inbound.foedtIKommune?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.foedekommuneterm = inbound.foedtIKommune?.verdi
            log.warn("Element not found in Kommune: " + inbound.foedtIKommune?.verdi)
        }

    }

    private fun getLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.foedtILand?.verdi.isNullOrEmpty() && !(land.betydninger.getValue(inbound.foedtILand?.verdi).isEmpty())) {
                personaliaKodeverk.landterm = land.betydninger.getValue(inbound.foedtILand?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.landterm = inbound.foedtILand?.verdi
            log.warn("Element not found in Land: " + inbound.foedtILand?.verdi)
        }

    }


    private fun getUtenlandskAdresseTerm(land: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.utenlandskAdresse?.land.isNullOrEmpty()) {
                personaliaKodeverk.utenlandskadresseterm = land.betydninger.getValue(inbound.adresseinfo?.utenlandskAdresse?.land)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskadresseterm = inbound.adresseinfo?.utenlandskAdresse?.land
            log.warn("Element not found in Utenlandskadresse: " + inbound.adresseinfo?.utenlandskAdresse?.land)
        }

    }

    private fun getKjonnTerm(kjonn: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.kjonn.isNullOrEmpty() && !(kjonn.betydninger.getValue(inbound.kjonn).isEmpty())) {
                personaliaKodeverk.kjonnterm = kjonn.betydninger.getValue(inbound.kjonn)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.kjonnterm = inbound.kjonn
            log.warn("Element not found in Kjonn: " + inbound.kjonn)
        }

    }

    private fun getPostadresseLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.adresseinfo?.postadresse?.land.isNullOrEmpty()) {
                personaliaKodeverk.postadresselandterm = land.betydninger.getValue(inbound.adresseinfo?.postadresse?.land)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.landterm = inbound.foedtILand?.verdi
            log.warn("Element not found in Land: " + inbound.foedtILand?.verdi)
        }
    }


    private fun getUtenlandskBankLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.utenlandskBank?.land?.verdi.isNullOrEmpty() && !(land.betydninger.getValue(inbound.utenlandskBank?.land?.verdi).isEmpty())) {
                personaliaKodeverk.utenlandskbanklandterm = land.betydninger.getValue(inbound.utenlandskBank?.land?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskbanklandterm = inbound.utenlandskBank?.land?.verdi
            log.warn("Element not found in Land (Utenlandsk bank): " + inbound.utenlandskBank?.land?.verdi)
        }

    }


    private fun getUtenlandskBankValutaTerm(valuta: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        try {
            if (!inbound.utenlandskBank?.valuta?.verdi.isNullOrEmpty() && !(valuta.betydninger.getValue(inbound.utenlandskBank?.valuta?.verdi).isEmpty())) {
                personaliaKodeverk.utenlandskbankvalutaterm = valuta.betydninger.getValue(inbound.utenlandskBank?.valuta?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            personaliaKodeverk.utenlandskbankvalutaterm = inbound.utenlandskBank?.valuta?.verdi
            log.warn("Element not found in Valuta: " + inbound.utenlandskBank?.valuta?.verdi)
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
        if (inbound?.bydel != null) {
            return inbound.bydel
        }
        else
        {
            return inbound?.kommune
        }
    }
}

