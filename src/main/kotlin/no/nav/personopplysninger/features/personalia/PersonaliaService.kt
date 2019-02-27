package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
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
        log.warn("hentetKjonn " + hentetKjonn)
        val land = kodeverkConsumer.hentLandKoder(inbound.foedtILand)
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}