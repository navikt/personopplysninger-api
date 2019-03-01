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
       // val kommune = kodeverkConsumer.hentKommuner(inbound.foedtIKommune.toString())
        val land = kodeverkConsumer.hentLandKoder((inbound.foedtILand).toString())
        val status = kodeverkConsumer.hentPersonstatus(inbound.status.toString())
        //val postnummer = kodeverkConsumer.hentPostnummer(
        val sivilstand = kodeverkConsumer.hentSivilstand(inbound.sivilstand.toString())
        val spraak = kodeverkConsumer.hentSpraak(inbound.spraak.toString())
        val statsborgerskap = kodeverkConsumer.hentStatsborgerskap(inbound.statsborgerskap.toString())

        var personkjonn = kjonn.betydninger.getValue(inbound.kjonn)[0].beskrivelser
    //    var personkommune = kommune.betydninger.getValue(inbound.foedtIKommune.toString())[0].beskrivelser
        var personland = land.betydninger.getValue(inbound.foedtILand.toString())[0].beskrivelser
        var personstatus = status.betydninger.getValue(inbound.kjonn)[0].beskrivelser
        var personsivilstand = sivilstand.betydninger.getValue(inbound.kjonn)[0].beskrivelser
        var personspraak = spraak.betydninger.getValue(inbound.kjonn)[0].beskrivelser
        val personstatsborgerskap = statsborgerskap.betydninger.getValue(inbound.statsborgerskap.toString())[0].beskrivelser
        val kjonnterm = personkjonn.getValue(kodeverkspraak).term
    //    val kommuneterm = personkommune.getValue(kodeverkspraak).term
        val landterm = personland.getValue(kodeverkspraak).term
        val statusterm = personstatus.getValue(kodeverkspraak).term
        val sivilstandterm = personsivilstand.getValue(kodeverkspraak).term
        val spraakterm = personspraak.getValue(kodeverkspraak).term
        val statsborgerskapterm = personstatsborgerskap.getValue(kodeverkspraak).term
        log.warn("kodeverkresult " + kjonnterm + " " + " " + landterm + " " + statusterm + " " + sivilstandterm + " " + spraakterm + " " + statsborgerskapterm)

        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}