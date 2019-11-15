package no.nav.personopplysninger.oppslag.kodeverk

import no.nav.log.MDCConstants
import no.nav.personopplysninger.features.ConsumerFactory
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.kodeverk.exceptions.KodeverkConsumerException
import org.slf4j.MDC
import org.springframework.cache.annotation.Cacheable

import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.Response
import java.net.URI

import javax.ws.rs.core.Response.Status.Family.SUCCESSFUL
import no.nav.personopplysninger.features.ConsumerFactory.readEntity

open class KodeverkConsumer constructor(
        private val client: Client,
        private val endpoint: URI
) {
    @Cacheable("retningsnummer")
    open fun hentRetningsnumre(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Retningsnumre/koder/betydninger", true))
    }

    @Cacheable("kjonn")
    open fun hentKjonn(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Kjønnstyper/koder/betydninger", true))
    }

    @Cacheable("kommune")
    open fun hentKommuner(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Kommuner/koder/betydninger", false))
    }

    @Cacheable("land")
    open fun hentLandKoder(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Landkoder/koder/betydninger", false))
    }

    @Cacheable("status")
    open fun hentPersonstatus(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Personstatuser/koder/betydninger", true))
    }

    @Cacheable("postnr")
    open fun hentPostnummer(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Postnummer/koder/betydninger", true))
    }

    @Cacheable("sivilstand")
    open fun hentSivilstand(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Sivilstander/koder/betydninger", true))
    }

    @Cacheable("spraak")
    open fun hentSpraak(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Språk/koder/betydninger", true))
    }

    @Cacheable("valuta")
    open fun hentValuta(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/Valutaer/koder/betydninger", true))
    }

    @Cacheable("statsborgerskap")
    open fun hentStatsborgerskap(): GetKodeverkKoderBetydningerResponse {
        return hentKodeverkBetydning(getBuilder("v1/kodeverk/StatsborgerskapFreg/koder/betydninger", true))
    }

    private fun getBuilder(path: String, eksluderUgyldige: Boolean?): Invocation.Builder {
        return client.target(endpoint)
                .path(path)
                .queryParam("spraak", SPRAAK)
                .queryParam("ekskluderUgyldige", eksluderUgyldige)
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", ConsumerFactory.CONSUMER_ID)
    }

    private fun hentKodeverkBetydning(request: Invocation.Builder): GetKodeverkKoderBetydningerResponse {
        try {
            request.get()
                    .use { response -> return readResponseBetydning(response) }
        } catch (e: KodeverkConsumerException) {
            throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere kodeverk. endpoint=[$endpoint]."
            throw KodeverkConsumerException(msg, e)
        }
    }

    private fun readResponseBetydning(r: Response): GetKodeverkKoderBetydningerResponse {
        if (SUCCESSFUL != r.statusInfo.family) {
            val msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.status + "]."
            throw KodeverkConsumerException(msg + " - " + readEntity(String::class.java, r))
        } else {
            return readEntity(GetKodeverkKoderBetydningerResponse::class.java, r)
        }
    }

    companion object {
        private val SPRAAK = "nb"
    }
}
