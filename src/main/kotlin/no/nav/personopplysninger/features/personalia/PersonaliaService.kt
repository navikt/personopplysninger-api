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

    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        var inbound = personConsumer.hentPersonInfo(fodselsnr)
        val land = kodeverkConsumer.hentLandKoder(inbound.foedtILand)
        val hentet = kodeverkConsumer.hentKjonn(inbound.kjonn)
        var test5 = hentet.betydninger.getValue(inbound.kjonn)[0].beskrivelser
        var test6 = hentet.betydninger.getValue(inbound.kjonn)[0].beskrivelser.size
        log.warn("test6 " + test6)
        log.warn("aa" + test5.values)
        val term = test5.getValue(inbound.kjonn)?.term
        log.warn("term " + term);
        val tekst = test5.getValue(inbound.kjonn)?.tekst
        log.warn("tekst " + tekst)
    //    var test7 = test6.toString()
     //   log.warn("test7" + test7);
      //  log.warn("hentetKjonn7 " + hentetKjonn.getValue(inbound.kjonn))
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}