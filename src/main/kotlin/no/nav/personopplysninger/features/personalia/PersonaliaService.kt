package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
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

        if (!inbound.kjonn.isNullOrEmpty()) { personaliaKodeverk.kjonnterm = kjonn.betydninger.getValue(inbound.kjonn)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.foedtILand?.verdi.isNullOrEmpty()) { personaliaKodeverk.landterm = land.betydninger.getValue(inbound.foedtILand?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.foedtIKommune?.verdi.isNullOrEmpty() && !nullstring.equals(inbound.foedtIKommune?.verdi))  { personaliaKodeverk.foedekommuneterm = foedtkommune.betydninger.getValue(inbound.foedtIKommune?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.adresseinfo?.boadresse?.kommune.isNullOrEmpty())  { personaliaKodeverk.bostedskommuneterm = bostedskommune.betydninger.getValue(inbound.adresseinfo?.boadresse?.kommune)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.adresseinfo?.boadresse?.postnummer.isNullOrEmpty())  { personaliaKodeverk.bostedpostnummerterm = postbostedsnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.adresseinfo?.postadresse?.postnummer.isNullOrEmpty())  { personaliaKodeverk.postnummerterm = postnummer.betydninger.getValue(inbound.adresseinfo?.postadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.adresseinfo?.tilleggsadresse?.postnummer.isNullOrEmpty()) {personaliaKodeverk.tilleggsadresseterm = posttilleggsnummer.betydninger.getValue(inbound.adresseinfo?.tilleggsadresse?.postnummer)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.status?.kode?.verdi.isNullOrEmpty())  {  personaliaKodeverk.statusterm = status.betydninger.getValue(inbound.status?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.sivilstand?.kode?.verdi.isNullOrEmpty())  { personaliaKodeverk.sivilstandterm = sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.spraak?.kode?.verdi.isNullOrEmpty())  { personaliaKodeverk.spraakterm = spraak?.betydninger!!.getValue(inbound.spraak?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }
        if (!inbound.statsborgerskap?.kode?.verdi.isNullOrEmpty()) { personaliaKodeverk.stasborgerskapterm = statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term }

        return PersonaliaOgAdresserTransformer.toOutbound(inbound, personaliaKodeverk)
    }
}