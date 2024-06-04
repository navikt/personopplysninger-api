package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
class KodeverkBetydningerResponse {
    private val logger = LoggerFactory.getLogger(KodeverkBetydningerResponse::class.java)

    val betydninger: Map<String, List<Betydning>> = emptyMap()

    fun tekst(key: String): String {
        return betydninger[key]?.first()?.tekst()
            ?: key.also { logger.warn("Feil ved utledning av kodeverkstekst for $key", Throwable()) }
    }

    fun term(key: String): String {
        return betydninger[key]?.first()?.term()
            ?: key.also { logger.warn("Feil ved utledning av kodeverksterm for $key", Throwable()) }
    }
}
