package no.nav.personopplysninger.features.endreopplysninger

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.log.MDCConstants
import no.nav.personopplysninger.features.ConsumerException
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.personopplysninger.features.ConsumerFactory.*
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring
import no.nav.personopplysninger.features.endreopplysninger.domain.Error
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.personalia.dto.getJson
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.net.URI
import javax.ws.rs.HttpMethod
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

class PersonMottakConsumer constructor(
        private val client: Client,
        private val endpoint: URI
)  {
    private val log = LoggerFactory.getLogger(PersonMottakConsumer::class.java)

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

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer, systemUserToken: String, httpMethod: String): EndringTelefon {
        val request = buildEndreRequest(fnr, systemUserToken, URL_TELEFONNUMMER)
        return sendEndring(request, telefonnummer, systemUserToken, httpMethod, EndringTelefon::class.java)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer, systemUserToken: String): EndringKontonummer {
        val request = buildEndreRequest(fnr, systemUserToken, URL_KONTONUMMER)
        return sendEndring(request, kontonummer, systemUserToken, HttpMethod.POST, EndringKontonummer::class.java)
    }

    fun endreGateadresse(fnr: String, gateadresse: Gateadresse, systemUserToken: String): EndringGateadresse {
        val request = buildEndreRequest(fnr, systemUserToken, URL_GATEADRESSE)
        return sendEndring(request, gateadresse, systemUserToken, HttpMethod.POST, EndringGateadresse::class.java)
    }

    fun endreStedsadresse(fnr: String, stedsadresse: Stedsadresse, systemUserToken: String): EndringStedsadresse {
        val request = buildEndreRequest(fnr, systemUserToken, URL_STEDSADRESSE)
        return sendEndring(request, stedsadresse, systemUserToken, HttpMethod.POST, EndringStedsadresse::class.java)
    }

    fun endrePostboksadresse(fnr: String, postboksadresse: Postboksadresse, systemUserToken: String): EndringPostboksadresse {
        val request = buildEndreRequest(fnr, systemUserToken, URL_POSTBOKSADRESSE)
        return sendEndring(request, postboksadresse, systemUserToken, HttpMethod.POST, EndringPostboksadresse::class.java)
    }

    fun endreUtenlandsadresse(fnr: String, utenlandsadresse: Utenlandsadresse, systemUserToken: String): EndringUtenlandsadresse {
        val request = buildEndreRequest(fnr, systemUserToken, URL_UTENLANDSADRESSE)
        return sendEndring(request, utenlandsadresse, systemUserToken, HttpMethod.POST, EndringUtenlandsadresse::class.java)
    }

    fun opphoerKontaktadresse(fnr: String, kontaktadresseType: KontaktadresseType, systemUserToken: String): EndringOpphoerAdresse {
        val url = if (kontaktadresseType == KontaktadresseType.NORSK)
            URL_OPPHOER_KONTAKTADRESSE_NORSK
        else
            URL_OPPHOER_KONTAKTADRESSE_UTENLANDSK
        val request = buildEndreRequest(fnr, systemUserToken, url)
        return sendEndring(request, null, systemUserToken, HttpMethod.PUT, EndringOpphoerAdresse::class.java)
    }

    private fun getBuilder(path: String, systemUserToken: String): Invocation.Builder {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Token", BEARER + systemUserToken)
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
    }

    private fun buildEndreRequest(fnr: String, systemUserToken: String, path: String): Invocation.Builder {
        return getBuilder(path, systemUserToken)
                .header("Nav-Personident", fnr)
    }

    private fun buildPollEndringRequest(url: String, systemUserToken: String): Invocation.Builder {
        return getBuilder(url, systemUserToken)
    }

    private fun <T : Endring<T>> sendEndring(request: Invocation.Builder, entitetSomEndres: Any?, systemUserToken: String, httpMethod: String, c: Class<T>): T {
        try {
            (if (entitetSomEndres == null)
                request.method(httpMethod, Entity.text(""))
            else
                request.method(httpMethod, Entity.entity(entitetSomEndres, MediaType.APPLICATION_JSON)))
                    .use {response -> return readResponseAndPollStatus(response, systemUserToken, c) }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun <T : Endring<T>> readResponseAndPollStatus(response: Response, systemUserToken: String, c: Class<T>): T {
        if (response.status == HTTP_CODE_423) {
            val endring = getEndring(c, "PENDING")
            endring.error = readEntity<Error>(Error::class.java, response)
            log.info("Oppdatering avvist pga status pending.")
            return endring
        } else if (response.status == HTTP_CODE_422) {
            val endring = getEndring(c, "ERROR")
            val err: Error =  readEntity<Error>(Error::class.java, response)
            log.error("Fikk valideringsfeil: " + getJson(err))
            endring.error = err
            return endring
        } else if (SUCCESSFUL != response.statusInfo.family) {
            val msg = "Forsøkte å konsumere person_mottak. endpoint=[" + endpoint + "], HTTP response status=[" + response.status + "]."
            throw ConsumerException(msg + " - " + readEntity<String>(String::class.java, response))
        } else {
            val pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION)
            var endring: T? = null
            var i = 0
            do {
                try {
                    Thread.sleep(SLEEP_TIME_MS.toLong())
                } catch (ie: InterruptedException) {
                    throw ConsumerException("Fikk feil under polling på status", ie)
                }

                val pollResponse = buildPollEndringRequest(pollEndringUrl, systemUserToken).get()
                endring = readEntities<T>(c, pollResponse).get(0)
            } while (++i < MAX_POLLS && endring!!.isPending)
            log.info("Antall polls for status: $i")

            if (!endring!!.isDoneWithoutTpsError) {
                endring.createValidationErrorIfTpsHasError()
                var json = ""
                try {
                    json = ObjectMapper().writeValueAsString(endring)
                } catch (jpe: JsonProcessingException) {
                }

                log.warn("Endring var ikke Done og/eller hadde TPS error. \n$json")
            }
            return endring
        }
    }

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
