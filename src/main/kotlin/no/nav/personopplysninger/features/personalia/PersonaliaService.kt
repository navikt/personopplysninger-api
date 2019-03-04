package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.Beskrivelse

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val kodeverkspraak = "nb";

    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        var inbound = personConsumer.hentPersonInfo(fodselsnr)
        val kjonn = kodeverkConsumer.hentKjonn(inbound.kjonn)
        val kommune = kodeverkConsumer.hentKommuner(inbound.foedtIKommune?.verdi)
        val land = kodeverkConsumer.hentLandKoder((inbound.foedtILand?.verdi))
        val status = kodeverkConsumer.hentPersonstatus(inbound.status?.kode?.verdi)
        val postnummer = kodeverkConsumer.hentPostnummer(inbound.adresseinfo?.postadresse?.postnummer)
        val sivilstand = kodeverkConsumer.hentSivilstand(inbound.sivilstand?.kode?.verdi)
        val spraak = kodeverkConsumer.hentSpraak(inbound.spraak?.kode?.verdi)
        val statsborgerskap = kodeverkConsumer.hentStatsborgerskap(inbound.statsborgerskap?.kode?.verdi)

        var personkjonn = kjonn?.let {kjonn.betydninger.getValue(inbound.kjonn)[0]?.beskrivelser }
        var personkommune = kommune?.let {kommune.betydninger.getValue(inbound.foedtIKommune?.verdi)[0]?.beskrivelser}
        var personland = land?.let {land.betydninger.getValue(inbound.foedtILand?.verdi)[0]?.beskrivelser}
        var personpostnummer = postnummer?.let {postnummer.betydninger.getValue(inbound.adresseinfo?.boadresse?.postnummer)[0]?.beskrivelser}
        var personstatus = status?.let {status.betydninger.getValue(inbound.status?.kode?.verdi)[0]?.beskrivelser}
        var personsivilstand = sivilstand?.let {sivilstand.betydninger.getValue(inbound.sivilstand?.kode?.verdi)[0]?.beskrivelser}
        var personspraak = spraak?.let {spraak.betydninger.getValue(inbound.spraak?.kode?.verdi)[0]?.beskrivelser}
        var personstatsborgerskap = statsborgerskap?.let {statsborgerskap.betydninger.getValue(inbound.statsborgerskap?.kode?.verdi)[0]?.beskrivelser}

        val kjonnterm = personkjonn?.getValue(kodeverkspraak)?.term
        val kommuneterm = personkommune?.getValue(kodeverkspraak)?.term
        val landterm = personland?.getValue(kodeverkspraak)?.term
        val postnummerterm = personpostnummer?.getValue(kodeverkspraak)?.term
        val statusterm = personstatus?.getValue(kodeverkspraak)?.term
        val sivilstandterm = personsivilstand?.getValue(kodeverkspraak)?.term
        val spraakterm = personspraak?.getValue(kodeverkspraak)?.term
        val statsborgerskapterm = personstatsborgerskap?.getValue(kodeverkspraak)?.term
        log.warn("kodeverkresult " + kjonnterm + " " + " " + landterm + " " + kommuneterm + " " + statusterm + " " + sivilstandterm + " " + spraakterm + " " + statsborgerskapterm+ " " + postnummerterm)

        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}