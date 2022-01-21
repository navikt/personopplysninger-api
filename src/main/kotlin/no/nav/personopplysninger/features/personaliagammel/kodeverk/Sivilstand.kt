package no.nav.personopplysninger.features.personaliagammel.kodeverk

import org.slf4j.Logger
import org.slf4j.LoggerFactory

enum class Sivilstand(val beskrivelse: String) {

    ENKE("Enke/-mann"),
    GIFT("Gift"),
    GJPA("Gjenlevende partner"),
    GLAD("Gift, lever adskilt"),
    NULL("Uoppgitt"),
    REPA("Registrert partner"),
    SAMB("Samboer"),
    SEPA("Separert partner"),
    SEPR("Separert"),
    SKIL("Skilt"),
    SKPA("Skilt partner"),
    UGIF("Ugift");

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Sivilstand::class.java)

        fun dekode(kode: String): String {
            return try {
                valueOf(kode).beskrivelse
            } catch (e: IllegalArgumentException) {
                log.warn("Enum for kodeverk for sivilstand mangler gitt kode [$kode]", e)
                "-"
            }
        }
    }

}