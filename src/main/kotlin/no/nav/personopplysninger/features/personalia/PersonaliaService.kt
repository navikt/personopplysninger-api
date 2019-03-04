package no.nav.personopplysninger.features.personalia

import no.nav.dkif.kontaktinformasjon.DigitalKontaktinfoBolk
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer,
        private var kontaktinfoConsumer: KontaktinfoConsumer
) {

    fun hentPersoninfo(fodselsnr: String): PersonaliaOgAdresser {
        val inbound = personConsumer.hentPersonInfo(fodselsnr)
        // TODO Are IN-702: Oppslag i kodeverkstjeneste
        return PersonaliaOgAdresserTransformer.toOutbound(inbound)
    }

    fun hentKontaktinformasjon(fodselsnr: Array<String>): DigitalKontaktinfoBolk {
        return kontaktinfoConsumer.hentKontaktinformasjon(fodselsnr)
    }
}
