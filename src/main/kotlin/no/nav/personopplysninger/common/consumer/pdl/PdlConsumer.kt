package no.nav.personopplysninger.common.consumer.pdl

import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import io.ktor.client.request.header
import no.nav.pdl.generated.dto.HentKontaktadresseQuery
import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.HentTelefonQuery
import no.nav.personopplysninger.config.BEARER
import no.nav.personopplysninger.config.CONSUMER_ID
import no.nav.personopplysninger.config.Environment
import no.nav.personopplysninger.config.HEADER_AUTHORIZATION
import no.nav.personopplysninger.config.HEADER_NAV_CALL_ID
import no.nav.personopplysninger.config.HEADER_NAV_CONSUMER_ID
import no.nav.tms.token.support.tokendings.exchange.TokendingsService
import org.slf4j.LoggerFactory
import java.util.*

private const val HEADER_TEMA = "tema"
private const val HEADER_BEHANDLINGSNUMMER = "behandlingsnummer"
private const val RETT_PERSONOPPLYSNINGER = "RPO"
private const val BEHANDLINGSNUMMER_PERSONOPPLYSNINGER = "B258"

private val logger = LoggerFactory.getLogger(PdlConsumer::class.java)

class PdlConsumer(
    private val client: GraphQLKtorClient,
    private val environment: Environment,
    private val tokenDingsService: TokendingsService,
) {
    suspend fun hentPerson(token: String, ident: String): HentPersonQuery.Result {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlTargetApp)

        val response = client.execute(HentPersonQuery(HentPersonQuery.Variables(ident = ident))) {
            header(HEADER_AUTHORIZATION, BEARER + accessToken)
            header(HEADER_NAV_CALL_ID, UUID.randomUUID())
            header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            header(HEADER_TEMA, RETT_PERSONOPPLYSNINGER)
            header(HEADER_BEHANDLINGSNUMMER, BEHANDLINGSNUMMER_PERSONOPPLYSNINGER)
        }
        if (response.errors.isNullOrEmpty()) {
            /*val warnings = response.extensions?.warnings
            if (!warnings.isNullOrEmpty()) {
                logWarnings(warnings)
            }
             */
            return response.data!!
        } else {
            /*
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
             */
            throw Exception()
        }
    }

    suspend fun hentKontaktadresse(token: String, ident: String): HentKontaktadresseQuery.Result {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlTargetApp)

        val response = client.execute(HentKontaktadresseQuery(HentKontaktadresseQuery.Variables(ident = ident))) {
            header(HEADER_AUTHORIZATION, BEARER + accessToken)
            header(HEADER_NAV_CALL_ID, UUID.randomUUID())
            header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            header(HEADER_TEMA, RETT_PERSONOPPLYSNINGER)
            header(HEADER_BEHANDLINGSNUMMER, BEHANDLINGSNUMMER_PERSONOPPLYSNINGER)
        }
        if (response.errors.isNullOrEmpty()) {
            /*val warnings = response.extensions?.warnings
            if (!warnings.isNullOrEmpty()) {
                logWarnings(warnings)
            }
             */
            return response.data!!
        } else {
            /*
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
             */
            throw Exception()
        }
    }

    suspend fun hentTelefon(token: String, ident: String): HentTelefonQuery.Result {
        val accessToken = tokenDingsService.exchangeToken(token, environment.pdlTargetApp)

        val response = client.execute(HentTelefonQuery(HentTelefonQuery.Variables(ident = ident))) {
            header(HEADER_AUTHORIZATION, BEARER + accessToken)
            header(HEADER_NAV_CALL_ID, UUID.randomUUID())
            header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            header(HEADER_TEMA, RETT_PERSONOPPLYSNINGER)
            header(HEADER_BEHANDLINGSNUMMER, BEHANDLINGSNUMMER_PERSONOPPLYSNINGER)
        }
        if (response.errors.isNullOrEmpty()) {
            /*val warnings = response.extensions?.warnings
            if (!warnings.isNullOrEmpty()) {
                logWarnings(warnings)
            }
             */
            return response.data!!
        } else {
            /*
            throw RuntimeException(consumerErrorMessage(endpoint, response.status.value, response.body()))
             */
            throw Exception()
        }
    }

    /*
    private fun logWarnings(warnings: List<PdlWarning>) {
        warnings.forEach {
            try {
                logger.warn("Advarsel fra PDL: ${it.message}. Detaljer: ${it.details}.")
            } catch (e: Exception) {
                logger.warn("Fikk advarsel fra PDL (deserialisering av advarsel feilet)")
            }
        }
    }
    */
}