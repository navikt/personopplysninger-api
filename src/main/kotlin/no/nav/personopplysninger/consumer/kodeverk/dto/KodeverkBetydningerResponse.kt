package no.nav.personopplysninger.consumer.kodeverk.dto

import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
class KodeverkBetydningerResponse {
    val betydninger: Map<String, List<Betydning>> = emptyMap()

    fun tekst(key: String): String {
        try {
            return betydninger[key]?.first()?.tekst()!!
        } catch (e: NullPointerException) {
            logger.warn("Feil ved utledning av kodeverkstekst for $key", e)
            return key
        }
    }

    fun term(key: String): String {
        try {
            return betydninger[key]?.first()?.term()!!
        } catch (e: NullPointerException) {
            logger.warn("Feil ved utledning av kodeverksterm for $key", e)
            return key
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(KodeverkBetydningerResponse::class.java)
    }
}
