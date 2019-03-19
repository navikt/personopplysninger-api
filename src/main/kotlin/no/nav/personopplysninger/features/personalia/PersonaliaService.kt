package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse

import no.nav.personopplysninger.features.personalia.dto.outbound.Kontaktinformasjon

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.KontaktinformasjonTransformer
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Personinfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kontaktinfoConsumer: KontaktinfoConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val kodeverkspraak = "nb"
    private val nullstring = "0000"
    private var personaliaKodeverk = PersonaliaKodeverk()

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

        if (!inbound.kjonn.isNullOrEmpty()) {
            getKjonnTerm(kjonn, inbound)
        }
        if (!inbound.foedtILand?.verdi.isNullOrEmpty()) {
            getLandTerm(land, inbound)
        }
        if (!inbound.foedtIKommune?.verdi.isNullOrEmpty() && !nullstring.equals(inbound.foedtIKommune?.verdi)) {
            getKommuneTerm(foedtkommune, inbound)
        }
        if (!inbound.adresseinfo?.boadresse?.kommune.isNullOrEmpty()) {
            getBostedskommuneTerm(bostedskommune, inbound)
        }
        if (!inbound.adresseinfo?.boadresse?.postnummer.isNullOrEmpty()) {
            getBostedpostnummerTerm(postbostedsnummer, inbound)
        }
        if (!inbound.adresseinfo?.postadresse?.postnummer.isNullOrEmpty()) {
            getPostadressePostnummerTerm(postnummer, inbound)
        }
        if (!inbound.adresseinfo?.tilleggsadresse?.postnummer.isNullOrEmpty()) {
            getTilleggsadresseTerm(posttilleggsnummer, inbound)
        }
        if (!inbound.status?.kode?.verdi.isNullOrEmpty()) {
            getStatusTerm(status, inbound)
        }
        if (!inbound.sivilstand?.kode?.verdi.isNullOrEmpty()) {
            getSivilstandTerm(sivilstand, inbound)
        }
        if (!inbound.spraak?.kode?.verdi.isNullOrEmpty()) {
            getSpraakTerm(spraak, inbound)
        }
        if (!inbound.statsborgerskap?.kode?.verdi.isNullOrEmpty()) {
            getStatsborgerskapTerm(statsborgerskap, inbound)
        }

        return PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
    }

    private fun getPostadressePostnummerTerm(postnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.postnummerterm = postnummer.betydninger.getValue(inbound.adresseinfo?.postadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getBostedpostnummerTerm(postbostedsnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.bostedpostnummerterm = postbostedsnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getBostedskommuneTerm(bostedskommune: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.bostedskommuneterm = bostedskommune.betydninger.getValue(inbound.adresseinfo?.boadresse?.kommune)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getTilleggsadresseTerm(posttilleggsnummer: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.tilleggsadressepostnummerterm = posttilleggsnummer.betydninger.getValue(inbound.adresseinfo?.tilleggsadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getStatusTerm(status: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.statusterm = status.betydninger.getValue(inbound.status?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getSivilstandTerm(sivilstand: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.sivilstandterm = sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getSpraakTerm(spraak: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.spraakterm = spraak?.betydninger!!.getValue(inbound.spraak?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getStatsborgerskapTerm(statsborgerskap: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.stasborgerskapterm = statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getKommuneTerm(foedtkommune: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.foedekommuneterm = foedtkommune.betydninger.getValue(inbound.foedtIKommune?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.landterm = land.betydninger.getValue(inbound.foedtILand?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    private fun getKjonnTerm(kjonn: GetKodeverkKoderBetydningerResponse, inbound: Personinfo) {
        personaliaKodeverk.kjonnterm = kjonn.betydninger.getValue(inbound.kjonn)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        return KontaktinformasjonTransformer.toOutbound(inbound, fodselsnr)
    }
}

        // TODO Are IN-702: Oppslag i kodeverkstjeneste
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }

    fun hentKontaktinformasjon(fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
        return KontaktinformasjonTransformer.toOutbound(inbound, fodselsnr)
    }
}
