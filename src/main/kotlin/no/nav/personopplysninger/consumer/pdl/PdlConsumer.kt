package no.nav.personopplysninger.consumer.pdl

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import com.expediagroup.graphql.client.types.GraphQLClientRequest
import com.expediagroup.graphql.client.types.GraphQLClientResponse
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import no.nav.pdl.generated.dto.HentKontaktadresseQuery
import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.HentTelefonQuery
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.personopplysninger.utils.consumerErrorMessage
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.LoggerFactory
import java.util.*

class PdlConsumer(
    private val client: GraphQLKtorClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    private val logger = LoggerFactory.getLogger(PdlConsumer::class.java)

    suspend fun hentPerson(token: String, ident: String): HentPersonQuery.Result {
        val request = HentPersonQuery(HentPersonQuery.Variables(ident = ident))
        val response = exhangeTokenAndExecutePdlRequest(request, token)

        return response.data!!
    }

    suspend fun hentKontaktadresse(token: String, ident: String): HentKontaktadresseQuery.Result {
        val request = HentKontaktadresseQuery(HentKontaktadresseQuery.Variables(ident = ident))
        val response = exhangeTokenAndExecutePdlRequest(request, token)

        return response.data!!
    }

    suspend fun hentTelefon(token: String, ident: String): HentTelefonQuery.Result {
        val request = HentTelefonQuery(HentTelefonQuery.Variables(ident = ident))
        val response = exhangeTokenAndExecutePdlRequest(request, token)

        return response.data!!
    }

    private suspend fun <T : Any> exhangeTokenAndExecutePdlRequest(
        request: GraphQLClientRequest<T>,
        token: String
    ): GraphQLClientResponse<T> {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlTargetApp)
        val response = client.execute(request) { pdlHeaders(accessToken) }
        response.handleWarningsAndErrors()

        return response
    }

    private fun HttpRequestBuilder.pdlHeaders(accessToken: String) {
        HttpHeaders
        header(HEADER_AUTHORIZATION, BEARER + accessToken)
        header(HEADER_NAV_CALL_ID, UUID.randomUUID())
        header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
        header(HEADER_TEMA, RETT_PERSONOPPLYSNINGER)
        header(HEADER_BEHANDLINGSNUMMER, BEHANDLINGSNUMMER_PERSONOPPLYSNINGER)
    }

    private fun <T> GraphQLClientResponse<T>.handleWarningsAndErrors() {
        extensions?.get(WARNINGS_EXTENSION)?.let { logger.warn(it.toString()) }

        if (!errors.isNullOrEmpty()) {
            throw RuntimeException(consumerErrorMessage(environment.pdlUrl, errors.toString()))
        }
    }

    companion object {
        private const val HEADER_TEMA = "tema"
        private const val HEADER_BEHANDLINGSNUMMER = "behandlingsnummer"
        private const val RETT_PERSONOPPLYSNINGER = "RPO"
        private const val BEHANDLINGSNUMMER_PERSONOPPLYSNINGER = "B258"

        private const val WARNINGS_EXTENSION = "warnings"
    }
}
