package no.nav.personopplysninger.features.personalia.kodeverk

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

}