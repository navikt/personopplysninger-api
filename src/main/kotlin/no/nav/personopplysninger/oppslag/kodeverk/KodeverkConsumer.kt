package no.nav.personopplysninger.oppslag.kodeverk

import no.nav.log.MDCConstants
import no.nav.personopplysninger.consumerutils.*
import no.nav.personopplysninger.oppslag.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.oppslag.kodeverk.api.Kodeverk
import no.nav.personopplysninger.oppslag.kodeverk.exceptions.KodeverkConsumerException
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

    @Cacheable("kjonn")
    open fun hentKjonn(): Kodeverk {
        return hentKodeverkBetydning("Kjønnstyper", true)
    }

    @Cacheable("kommune")
    open fun hentKommuner(): Kodeverk {
        return hentKodeverkBetydning("Kommuner", false)
    }

    @Cacheable("land")
    open fun hentLandKoder(): Kodeverk {
        return hentKodeverkBetydning("Landkoder", false)
    }

    @Cacheable("status")
    open fun hentPersonstatus(): Kodeverk {
        return hentKodeverkBetydning("Personstatuser", true)
    }

    @Cacheable("postnr")
    open fun hentPostnummer(): Kodeverk {
        return hentKodeverkBetydning("Postnummer", true)
    }

    @Cacheable("sivilstand")
    open fun hentSivilstand(): Kodeverk {
        return hentKodeverkBetydning("Sivilstander", true)
    }

    @Cacheable("spraak")
    open fun hentSpraak(): Kodeverk {
        return hentKodeverkBetydning("Språk", true)
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

    @Cacheable("lovvalgmedl")
    open fun hentLovvalgMedl(): Kodeverk {
        return hentKodeverkBetydning("LovvalgMedl", true)
    }

    @Cacheable("periodestatusmedl")
    open fun hentPeriodestatusMedl(): Kodeverk {
        return hentKodeverkBetydning("PeriodestatusMedl", true)
    }

    @Cacheable("statusaarsakmedl")
    open fun hentStatusaarsakMedl(): Kodeverk {
        return hentKodeverkBetydning("StatusaarsakMedl", true)
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
        try {
            val response = buildRequest("v1/kodeverk/$navn/koder/betydninger", eksluderUgyldige).get()
            if (SUCCESSFUL != response.statusInfo.family) {
                val msg = "Forsøkte å konsumere kodeverk. endpoint=[$endpoint], HTTP response status=[${response.status}], body=[${response.unmarshalBody<String>()}]."
                throw KodeverkConsumerException(msg)
            }
            return response.unmarshalBody<GetKodeverkKoderBetydningerResponse>()
                    .let { Kodeverk.fromKoderBetydningerResponse(navn, it) }
        } catch (e: KodeverkConsumerException) {
          throw e
        } catch (e: Exception) {
            val msg = "Forsøkte å konsumere kodeverk. endpoint=[$endpoint]."
            throw KodeverkConsumerException(msg, e)
        }
    }
}
