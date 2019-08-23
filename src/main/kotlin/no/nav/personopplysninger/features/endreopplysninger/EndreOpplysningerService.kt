package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.sts.STSConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService @Autowired constructor(
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer
) {

    fun endreTelefonnummer(fnr: String, nummer: Int): String? {
        personMottakConsumer.oppdaterTelefonnummer(fnr, nummer, getSystembrukerToken())
        return ""
    }

    private fun getSystembrukerToken(): String? {
        return stsConsumer.token?.access_token
    }

}