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
        val hentet = kodeverkConsumer.hentKjonn(inbound.kjonn)
        log.warn("hentetKjonn " + hentet.betydninger)
        log.warn("hentetKjonn2 " + hentet.betydninger.keys)
        log.warn("hentetKjonn3 " + hentet.betydninger.size)
        log.warn("hentetKjonn4 " + hentet.betydninger.values)
        val hentetKjonn = kodeverkConsumer.hentKjonn(inbound.kjonn).betydninger.getValue(inbound.kjonn)[0].beskrivelser
        val test = (hentet.betydninger.get("M"))
        log.warn("testbetydning " + test.isNullOrEmpty());
        val test2 = (hentet.betydninger.get(inbound.kjonn))
        log.warn("testbetydning2 " + test2.left());
      //  log.warn("hentetKjonn7 " + hentetKjonn.getValue(inbound.kjonn))
        val land = kodeverkConsumer.hentLandKoder(inbound.foedtILand)
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}