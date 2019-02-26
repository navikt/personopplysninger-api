package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
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
        val inbound = personConsumer.hentPersonInfo(fodselsnr)
        val kjonn = inbound.kjonn
        val land = inbound.foedtILand
        log.warn("Kjonn er " + kjonn)
        log.warn("Land er " + land)
        val kodeverklandkoder = kodeverkConsumer.hentLandKoder(land)
        log.warn("KodeverkLandkoder er " + kodeverklandkoder)
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }
}