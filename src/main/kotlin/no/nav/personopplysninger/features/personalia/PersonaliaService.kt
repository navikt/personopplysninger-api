package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer
) {

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val inbound = personConsumer.hentPersonInfo(fodselsnr)
        val thinDto = PersonaliaOgAdresserTransformer.toOutbound(inbound)
        val dtoWithFnr = thinDto.copy(personalia = thinDto.personalia.copy(fnr = fodselsnr))
        // TODO Are IN-702: Oppslag i kodeverkstjeneste
        return dtoWithFnr
    }
}