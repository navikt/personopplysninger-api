package no.nav.personopplysninger.features.personalia

import arrow.core.left
import arrow.core.rightIfNotNull
import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.Betydning
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderResponse
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
        var test5 = hentet.betydninger.getValue("M").get(0).beskrivelser.get("nb")
        log.warn("testx" + test5);
    //    var test7 = test6.toString()
     //   log.warn("test7" + test7);
      //  log.warn("hentetKjonn7 " + hentetKjonn.getValue(inbound.kjonn))
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}