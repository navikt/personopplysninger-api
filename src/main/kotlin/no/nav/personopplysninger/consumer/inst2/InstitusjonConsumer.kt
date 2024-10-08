package no.nav.personopplysninger.consumer.inst2

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.config.HEADER_NAV_PERSONIDENT
import no.nav.personopplysninger.consumer.inst2.dto.InnsynInstitusjonsopphold
import no.nav.personopplysninger.utils.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import java.util.*

class InstitusjonConsumer(
    private val client: HttpClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {

    suspend fun getInstitusjonsopphold(token: String, fnr: String): List<InnsynInstitusjonsopphold> {
        val accessToken = tokenDingsService.exchangeToken(token, environment.inst2TargetApp)
        val endpoint = environment.inst2Url.plus("/v1/person/innsyn")

        val response: HttpResponse =
            client.get(endpoint) {
                header(HEADER_AUTHORIZATION, BEARER + accessToken)
                header(HEADER_NAV_CALL_ID, UUID.randomUUID())
                header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
                header(HEADER_NAV_PERSONIDENT, fnr)
            }
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
        }
    }
}
