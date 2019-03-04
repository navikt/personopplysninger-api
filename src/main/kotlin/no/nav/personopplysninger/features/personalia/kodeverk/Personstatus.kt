package no.nav.personopplysninger.features.personalia.kodeverk

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

}