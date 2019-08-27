package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.sts.STSConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService @Autowired constructor(
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer
) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

//    fun endreTelefonnummer(fnr: String, nummer: Int): OppdaterTelefonnumerResponse? {
//        log.info("fnr= ".plus(fnr).plus(", sysbrukertoken= ".plus(getSystembrukerToken())))
//        return personMottakConsumer.oppdaterTelefonnummer(fnr, nummer, getSystembrukerToken())
//    }

    fun endreTelefonnummer(fnr: String, nummer: Int): String {
        log.info("fnr= ".plus(fnr).plus(", sysbrukertoken= ".plus(getSystembrukerToken())))
        return personMottakConsumer.oppdaterTelefonnummer(fnr, nummer, getSystembrukerToken())
    }

        private fun getSystembrukerToken(): String? {
        return stsConsumer.token?.access_token
    }

}