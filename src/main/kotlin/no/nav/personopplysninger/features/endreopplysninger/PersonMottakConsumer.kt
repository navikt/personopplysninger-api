package no.nav.personopplysninger.features.endreopplysninger

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.CONSUMER_ID
import no.nav.personopplysninger.consumerutils.ConsumerException
import no.nav.personopplysninger.consumerutils.unmarshalBody
import no.nav.personopplysninger.consumerutils.unmarshalList
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring
import no.nav.personopplysninger.features.endreopplysninger.domain.Personopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.EndreKontaktadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.EndringKontaktadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.opphoer.EndringOpphoerPersonopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.opphoer.OpphoerPersonopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndreTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.personalia.dto.getJson
import no.nav.personopplysninger.oppslag.sts.STSConsumer
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

class PersonMottakConsumer (
        private val client: Client,
        private val endpoint: URI,
        private val stsConsumer: STSConsumer
)  {
    private val log = LoggerFactory.getLogger(PersonMottakConsumer::class.java)

    private val HTTP_CODE_422 = 422
    private val HTTP_CODE_423 = 423

    private val BEARER = "Bearer "
    private val SLEEP_TIME_MS = 1000L
    private val MAX_POLLS = 3

    private val URL_KONTONUMMER = "/api/v1/endring/bankkonto"
    private val URL_GATEADRESSE = "/api/v1/endring/kontaktadresse/norsk/gateadresse"
    private val URL_POSTBOKSADRESSE = "/api/v1/endring/kontaktadresse/norsk/postboksadresse"
    private val URL_UTENLANDSADRESSE = "/api/v1/endring/kontaktadresse/utenlandsk"
    private val URL_STEDSADRESSE = "/api/v1/endring/kontaktadresse/norsk/stedsadresse"
    private val URL_OPPHOER_KONTAKTADRESSE_NORSK = "/api/v1/endring/kontaktadresse/norsk/opphoer"
    private val URL_OPPHOER_KONTAKTADRESSE_UTENLANDSK = "/api/v1/endring/kontaktadresse/utenlandsk/opphoer"

    private val URL_ENDRINGER = "/api/v1/endringer"

    fun endreTelefonnummer(fnr: String, endreTelefon: EndreTelefon, systemUserToken: String): EndringTelefon {
        return sendPdlEndring(endreTelefon, fnr, systemUserToken, URL_ENDRINGER)
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
        return sendBlankEndring(request, systemUserToken, EndringOpphoerAdresse::class.java)
    }

    fun endreKontaktadresse(fnr: String,
                            endreKontaktadresse: EndreKontaktadresse,
                            systemUserToken: String): EndringKontaktadresse {

        return sendPdlEndring(endreKontaktadresse, fnr, systemUserToken, URL_ENDRINGER)
    }

    fun slettPersonopplysning(fnr: String,
                              opphoerPersonopplysning: OpphoerPersonopplysning,
                              systemUserToken: String): EndringOpphoerPersonopplysning {
        return sendPdlEndring(opphoerPersonopplysning, fnr, systemUserToken, URL_ENDRINGER)
    }

    private fun getBuilder(path: String, systemUserToken: String): Invocation.Builder {
        return client.target(endpoint)
                .path(path)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Token", BEARER + systemUserToken)
                .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun buildEndreRequest(fnr: String, systemUserToken: String, path: String): Invocation.Builder {
        return getBuilder(path, systemUserToken)
                .header("Nav-Personident", fnr)
    }

    private fun buildPollEndringRequest(url: String, systemUserToken: String): Invocation.Builder {
        return getBuilder(url, systemUserToken)
    }

    private fun <T : Endring<T>> sendEndring(request: Invocation.Builder, entitetSomEndres: Any, systemUserToken: String, httpMethod: String, c: Class<T>): T {
        return try {
            request.method(httpMethod, Entity.entity(entitetSomEndres, MediaType.APPLICATION_JSON))
                .use {response -> readResponseAndPollStatus(response, systemUserToken, c) }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun <T : Endring<T>> sendBlankEndring(request: Invocation.Builder, systemUserToken: String, c: Class<T>): T {
        return try {
            request.method(HttpMethod.PUT, Entity.text(""))
                .use {response -> readResponseAndPollStatus(response, systemUserToken, c) }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private inline fun <reified T, reified R : Endring<R>> sendPdlEndring(entitetSomEndres: Personopplysning<T>,
                                                                          fnr: String,
                                                                          systemUserToken: String,
                                                                          path: String): R {

        val request = buildEndreRequest(fnr, systemUserToken, path)

        return try {
            request.method(
                    HttpMethod.POST,
                    Entity.entity(entitetSomEndres.asSingleEndring(), MediaType.APPLICATION_JSON)
            ).use {
                response -> readResponseAndPollStatus(response)
            }
        } catch (e: Exception) {
            val msg = "Forsøkte å endre personopplysning. endpoint=[$endpoint]."
            throw ConsumerException(msg, e)
        }
    }

    private fun <T : Endring<T>> readResponseAndPollStatus(response: Response, systemUserToken: String, clazz: Class<T>): T {
        log.info(response.readEntity(String::class.java))
        return when {
            response.status == HTTP_CODE_423 -> {
                getEndring(clazz, "REJECTED").apply {
                    error = response.unmarshalBody()
                    log.info("Oppdatering avvist pga status pending.")
                }
            } response.status == HTTP_CODE_422 -> {
                getEndring(clazz, "ERROR").apply {
                    error = response.unmarshalBody()
                    log.error("Fikk valideringsfeil: ${getJson(this)}")
                }
            } SUCCESSFUL != response.statusInfo.family -> {
                throw ConsumerException(
                        "Forsøkte å konsumere person_mottak. endpoint=[$endpoint], HTTP response status=[${response.status}] - ${response.unmarshalBody<String>()}."
                )
            } else -> {
                val pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION)
                buildPollEndringRequest(pollEndringUrl, systemUserToken)
                        .pollFor(clazz, SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private inline fun <reified T : Endring<T>> readResponseAndPollStatus(response: Response): T {
        return when {
            response.status == HTTP_CODE_423 -> {
                getEndring<T>("REJECTED").apply {
                    error = response.unmarshalBody()
                    log.info("Oppdatering avvist pga status pending.")
                }
            } response.status == HTTP_CODE_422 -> {
                getEndring<T>("ERROR").apply {
                    error = response.unmarshalBody()
                    log.error("Fikk valideringsfeil: ${getJson(this)}")
                }
            } SUCCESSFUL != response.statusInfo.family -> {
                throw ConsumerException(
                        "Forsøkte å konsumere person_mottak. endpoint=[$endpoint], HTTP response status=[${response.status}] - ${response.unmarshalBody<String>()}."
                )
            } else -> {
                val pollEndringUrl = response.getHeaderString(HttpHeaders.LOCATION)
                buildPollEndringRequest(pollEndringUrl, systemToken)
                        .pollFor(SLEEP_TIME_MS, MAX_POLLS)
            }
        }
    }

    private fun <T : Endring<T>> Invocation.Builder.pollFor(clazz: Class<T>, pollInterval: Long, maxPolls: Int): T {
        var endring: T
        var i = 0
        do {
            try {
                Thread.sleep(pollInterval)
            } catch (ie: InterruptedException) {
                throw ConsumerException("Fikk feil under polling på status", ie)
            }

            val pollResponse = get()
            endring = pollResponse.unmarshalList(clazz).get(0)
        } while (++i < maxPolls && endring.isPending)
        log.info("Antall polls for status: $i")

        if (!endring.confirmedOk) {
            endring.createValidationErrorIfTpsHasError()
            val json = runCatching {
                ObjectMapper().writeValueAsString(endring)
            }.getOrDefault("")
            log.warn("${endring.errorMessage}\n$json")
        }
        return endring
    }

    private inline fun <reified T : Endring<T>> Invocation.Builder.pollFor(pollInterval: Long, maxPolls: Int): T {
        var endring: T
        var i = 0
        do {
            try {
                Thread.sleep(pollInterval)
            } catch (ie: InterruptedException) {
                throw ConsumerException("Fikk feil under polling på status", ie)
            }

            val pollResponse = get()
            endring = pollResponse.unmarshalList<T>().get(0)
        } while (++i < maxPolls && endring.isPending)
        log.info("Antall polls for status: $i")

        if (!endring.confirmedOk) {
            endring.createValidationErrorIfTpsHasError()
            val json = runCatching {
                ObjectMapper().writeValueAsString(endring)
            }.getOrDefault("")
            log.warn("${endring.errorMessage}\n$json")
        }
        return endring
    }

    private fun <T : Endring<T>> getEndring(clazz: Class<T>, statusType: String): T {
        try {
            return clazz.newInstance().apply { this.statusType = statusType }
        } catch (e: Exception) {
            log.error("Fikk exception ved forsøk på å instansiere ${clazz.name}")
            throw RuntimeException(e)
        }
    }

    private inline fun <reified T : Endring<T>> getEndring(statusType: String): T {
        try {
            return T::class.java.newInstance().apply { this.statusType = statusType }
        } catch (e: Exception) {
            log.error("Fikk exception ved forsøk på å instansiere ${T::class.java.name}")
            throw RuntimeException(e)
        }
    }

    private val systemToken: String get() = stsConsumer.token.access_token
}
