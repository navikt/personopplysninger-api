package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(PersonaliaService::class.java)

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val inbound = personConsumer.hentPersonInfo(fodselsnr)
        val kjonn = inbound.kjonn
        val land = inbound.foedtILand
        log.warn("Kjonn er " + kjonn)
        val kodeverklandkoder = kodeverkConsumer.hentLandKoder(land)
        log.warn("KodeverkLandkoder er " + kodeverklandkoder)
        val kodeverkkjonn = kodeverkConsumer.hentKjonn(kjonn)
        log.warn("KodeverkKjonn er " + kodeverkkjonn)
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}