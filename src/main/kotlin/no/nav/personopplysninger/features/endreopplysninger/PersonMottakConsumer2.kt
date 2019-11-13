package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.Endring
import org.slf4j.LoggerFactory
import java.net.URI
import javax.ws.rs.client.Client

class PersonMottakConsumer2 constructor(
        private val client: Client,
        private val endpoint: URI
)  {
    private val log = LoggerFactory.getLogger(PersonMottakConsumer2::class.java)

    private val HTTP_CODE_422 = 422
    private val HTTP_CODE_423 = 423

    private val BEARER = "Bearer "
    private val SLEEP_TIME_MS = 1000
    private val MAX_POLLS = 3

    private val URL_TELEFONNUMMER = "/api/v1/endring/telefonnummer"
    private val URL_KONTONUMMER = "/api/v1/endring/bankkonto"
    private val URL_GATEADRESSE = "/api/v1/endring/kontaktadresse/norsk/gateadresse"
    private val URL_POSTBOKSADRESSE = "/api/v1/endring/kontaktadresse/norsk/postboksadresse"
    private val URL_UTENLANDSADRESSE = "/api/v1/endring/kontaktadresse/utenlandsk"
    private val URL_STEDSADRESSE = "/api/v1/endring/kontaktadresse/norsk/stedsadresse"
    private val URL_OPPHOER_KONTAKTADRESSE_NORSK = "/api/v1/endring/kontaktadresse/norsk/opphoer"
    private val URL_OPPHOER_KONTAKTADRESSE_UTENLANDSK = "/api/v1/endring/kontaktadresse/utenlandsk/opphoer"

    private fun <T : Endring<T>> getEndring(c: Class<T>, statusType: String): T {
        try {
            val endring = c.newInstance()
            endring.statusType = statusType
            return endring
        } catch (e: Exception) {
            log.error("Fikk exception ved forsøk på å instansiere " + c.name)
            throw RuntimeException(e)
        }
    }
}
