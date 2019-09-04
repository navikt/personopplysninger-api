package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.Adresse
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.EndringAdresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.features.sts.STSConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService @Autowired constructor(
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer, httpMethod: String): EndringTelefon {
        return personMottakConsumer.endreTelefonnummer(fnr, telefonnummer, getSystembrukerToken(), httpMethod)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, getSystembrukerToken())
    }

    fun endreAdresse(fnr: String, adresse: Adresse): EndringAdresse {
        return personMottakConsumer.endreAdresse(fnr, adresse, getSystembrukerToken())
    }

    fun hentRetningsnumre(): Array<RetningsnummerDTO> {
        return kodeverkConsumer.hentRetningsnumre().betydninger
                .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
                .sortedBy { it.land }
                .toTypedArray()
    }

    private fun getSystembrukerToken(): String? {
        return stsConsumer.token?.access_token
    }

}