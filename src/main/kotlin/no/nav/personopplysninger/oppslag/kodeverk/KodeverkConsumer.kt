package no.nav.personopplysninger.oppslag.kodeverk

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.common.log.MDCConstants
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.kodeverk.api.Kodeverk
import no.nav.personopplysninger.util.CONSUMER_ID
import no.nav.personopplysninger.util.ConsumerException
import no.nav.personopplysninger.util.JsonDeserialize.objectMapper
import no.nav.personopplysninger.util.consumerErrorMessage
import org.slf4j.MDC
import org.springframework.cache.annotation.Cacheable
import java.net.URI
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL

open class KodeverkConsumer constructor(
    private val client: Client,
    private val endpoint: URI
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

    private fun buildRequest(path: String, eksluderUgyldige: Boolean): Invocation.Builder {
        return client.target(endpoint)
            .path(path)
            .queryParam("spraak", "nb")
            .queryParam("ekskluderUgyldige", eksluderUgyldige)
            .request()
            .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
            .header("Nav-Consumer-Id", CONSUMER_ID)
    }

    private fun hentKodeverkBetydning(navn: String, eksluderUgyldige: Boolean): Kodeverk {
        buildRequest("v1/kodeverk/$navn/koder/betydninger", eksluderUgyldige).get().use { response ->
            val responseBody = response.readEntity(String::class.java)
            if (SUCCESSFUL != response.statusInfo.family) {
                throw ConsumerException(consumerErrorMessage(endpoint, response.status, responseBody))
            }
            return objectMapper.readValue<GetKodeverkKoderBetydningerResponse>(responseBody)
                .let { Kodeverk.fromKoderBetydningerResponse(navn, it) }
        }
    }
}
