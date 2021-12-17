package no.nav.personopplysninger.features.personaliagammel.kodeverk

import org.slf4j.Logger
import org.slf4j.LoggerFactory

enum class Personstatus(val beskrivelse: String) {
    ABNR("Aktivt BOSTNR"),
    ADNR("Aktivt"),
    BOSA("Bosatt"),
    DØD("Død"),
    DØDD("Død"),
    FOSV("Forsvunnet/savnet"),
    FØDR("Fødselsregistrert"),
    UFUL("Ufullstendig fødselsnr"),
    UREG("Uregistrert person"),
    UTAN("Utgått person annullert tilgang Fnr"),
    UTPE("Utgått person"),
    UTVA("Utvandret");

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Personstatus::class.java)

        fun dekode(kode: String): String {
            return try {
                valueOf(kode).beskrivelse
            } catch (e: IllegalArgumentException) {
                log.warn("Enum for kodeverk for personstatus mangler gitt kode [$kode]", e)
                "-"
            }
        }
    }

}