package no.nav.personopplysninger.features.personalia.kodeverk

import org.slf4j.Logger
import org.slf4j.LoggerFactory

enum class Kjoennstype(val beskrivelse: String) {
    M("Mann"),
    K("Kvinne"),
    U("Ukjent");

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Kjoennstype::class.java)

        fun dekode(kode: String): String {
            try {
                return Kjoennstype.valueOf(kode).beskrivelse
            } catch (e: IllegalArgumentException) {
                log.warn("Enum for kodeverk for kjønnstype mangler gitt kode [" + kode + "]", e)
                return "-"
            }
        }
    }
}