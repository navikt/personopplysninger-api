package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Personinfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonaliaService @Autowired constructor(
        private var personConsumer: PersonConsumer
){

    fun hentPersoninfo(fodselsnr: String): Personinfo {
        val personinfo = personConsumer.hentPersonInfo(fodselsnr)
        return personinfo
    }

}