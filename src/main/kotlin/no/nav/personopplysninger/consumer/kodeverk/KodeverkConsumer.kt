package no.nav.personopplysninger.consumer.kodeverk

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.consumer.*
import no.nav.personopplysninger.consumer.JsonDeserialize.objectMapper
import no.nav.personopplysninger.consumer.kodeverk.domain.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.consumer.kodeverk.domain.Kodeverk
import no.nav.personopplysninger.consumer.tokendings.TokenDingsService
import no.nav.personopplysninger.exception.ConsumerException
import no.nav.personopplysninger.exception.consumerErrorMessage
import no.nav.personopplysninger.util.getToken
import org.slf4j.MDC
import org.springframework.cache.annotation.Cacheable
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

open class KodeverkConsumer constructor(
    private val client: Client,
    private val endpoint: URI,
    private val tokenDingsService: TokenDingsService,
    private val targetApp: String?
) {
    @Cacheable("retningsnummer")
    open fun hentRetningsnumre(): Kodeverk {
        return hentKodeverkBetydning("Retningsnumre", true)
    }

    @Cacheable("kommune")
    open fun hentKommuner(): Kodeverk {
        return hentKodeverkBetydning("Kommuner", false)
    }

    @Cacheable("land")
    open fun hentLandKoder(): Kodeverk {
        return hentKodeverkBetydning("Landkoder", true)
    }

    @Cacheable("postnr")
    open fun hentPostnummer(): Kodeverk {
        return hentKodeverkBetydning("Postnummer", true)
    }

    @Cacheable("valuta")
    open fun hentValuta(): Kodeverk {
        return hentKodeverkBetydning("Valutaer", true)
    }

    @Cacheable("statsborgerskap")
    open fun hentStatsborgerskap(): Kodeverk {
        return hentKodeverkBetydning("StatsborgerskapFreg", true)
    }

    @Cacheable("dekningmedl")
    open fun hentDekningMedl(): Kodeverk {
        return hentKodeverkBetydning("DekningMedl", true)
    }

    @Cacheable("grunnlagmedl")
    open fun hentGrunnlagMedl(): Kodeverk {
        return hentKodeverkBetydning("GrunnlagMedl", true)
    }

    @Cacheable("spraak")
    open fun hentSpraak(): Kodeverk {
        return hentKodeverkBetydning("Språk", true)
    }

    private fun buildRequest(path: String, eksluderUgyldige: Boolean): Invocation.Builder {
        val selvbetjeningToken = getToken()
        val accessToken = tokenDingsService.exchangeToken(selvbetjeningToken, targetApp).accessToken
        return client.target(endpoint)
            .path(path)
            .queryParam("spraak", "nb")
            .queryParam("ekskluderUgyldige", eksluderUgyldige)
            .request()
            .header(HEADER_NAV_CALL_ID, MDC.get(MDCConstants.MDC_CALL_ID))
            .header(HEADER_NAV_CONSUMER_ID, CONSUMER_ID)
            .header(HEADER_AUTHORIZATION, BEARER + accessToken)
    }

    private fun hentKodeverkBetydning(navn: String, eksluderUgyldige: Boolean): Kodeverk {
        buildRequest("api/v1/kodeverk/$navn/koder/betydninger", eksluderUgyldige).get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper.readValue<GetKodeverkKoderBetydningerResponse>(responseBody)
                .let { Kodeverk.fromKoderBetydningerResponse(navn, it) }
        }
    }
}
