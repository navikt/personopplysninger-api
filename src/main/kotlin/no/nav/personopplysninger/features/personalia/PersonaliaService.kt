package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.Beskrivelse

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val kodeverkspraak = "nb"
    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)
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
        log.error("foedIkommune " + inbound.foedtIKommune?.verdi  + "land " + land + " " + bostedskommune)
        val personkjonn = kjonn?.let {kjonn.betydninger.getValue(inbound.kjonn)[0]?.beskrivelser }
        val personfoedtkommune = inbound.foedtIKommune?.let {foedtkommune.betydninger.getValue(inbound.foedtIKommune.verdi)[0]?.beskrivelser}
        val personbostedskommune = bostedskommune?.let {bostedskommune.betydninger.getValue(inbound.adresseinfo?.boadresse?.kommune)[0]?.beskrivelser}
        val personland = land?.let {land.betydninger.getValue(inbound.foedtILand?.verdi)[0]?.beskrivelser}
        val personbostedsnummer = postbostedsnummer?.let {postnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer)[0]?.beskrivelser}
        val personpostnummer = inbound.adresseinfo?.postadresse?.postnummer?.let {postnummer.betydninger.getValue(inbound.adresseinfo.postadresse.postnummer)[0]?.beskrivelser}
        val persontilleggpostnummer =posttilleggsnummer?.let {postnummer.betydninger.getValue(inbound.adresseinfo?.tilleggsadresse?.postnummer)[0]?.beskrivelser}
        val personstatus = status?.let {status.betydninger.getValue(inbound.status?.kode?.verdi)[0]?.beskrivelser}
        val personsivilstand = sivilstand?.let {sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi)[0]?.beskrivelser}
        val personspraak = inbound.spraak?.let {spraak?.betydninger!!.getValue(inbound.spraak.kode?.verdi)[0]?.beskrivelser}
        val personstatsborgerskap = statsborgerskap?.let {statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi)[0]?.beskrivelser}

        val kjonnterm = personkjonn?.getValue(kodeverkspraak)?.term
        val foedekommuneterm = personfoedtkommune?.getValue(kodeverkspraak)?.term
        val bostedskommuneterm = personbostedskommune?.getValue(kodeverkspraak)?.term
        val landterm = personland?.getValue(kodeverkspraak)?.term
        val postnummerterm = personpostnummer?.getValue(kodeverkspraak)?.term
        val bostedpostnummerterm = personbostedsnummer?.getValue(kodeverkspraak)?.term
        val tilleggsadresseterm = persontilleggpostnummer?.getValue(kodeverkspraak)?.term
        val statusterm = personstatus?.getValue(kodeverkspraak)?.term
        val sivilstandterm = personsivilstand?.getValue(kodeverkspraak)?.term
        val spraakterm = personspraak?.let {personspraak.getValue(kodeverkspraak)?.term}
        val statsborgerskapterm = personstatsborgerskap?.getValue(kodeverkspraak)?.term

        personaliaKodeverk.kjonnterm = kjonnterm
        personaliaKodeverk.foedekommuneterm = foedekommuneterm
        personaliaKodeverk.bostedskommuneterm = bostedskommuneterm
        personaliaKodeverk.landterm = landterm
        personaliaKodeverk.postnummerterm = postnummerterm
        personaliaKodeverk.bostedpostnummerterm = bostedpostnummerterm
        personaliaKodeverk.tilleggsadresseterm = tilleggsadresseterm
        personaliaKodeverk.sivilstandterm = sivilstandterm
        personaliaKodeverk.spraakterm = spraakterm
        personaliaKodeverk.stasborgerskapterm = statsborgerskapterm
        personaliaKodeverk.statusterm = statusterm

        return PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
    }
}