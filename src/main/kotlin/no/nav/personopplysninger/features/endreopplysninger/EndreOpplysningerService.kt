package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.Telefonnummer
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

    fun endreTelefonnummer(fnr: String, nummer: Int, landskode: String, type: String): EndringTelefon {
        val telefonnummer = Telefonnummer(landskode, nummer, type)
        return personMottakConsumer.oppdaterTelefonnummer(fnr, telefonnummer, getSystembrukerToken())
    }

        private fun getSystembrukerToken(): String? {
        return stsConsumer.token?.access_token
    }

}