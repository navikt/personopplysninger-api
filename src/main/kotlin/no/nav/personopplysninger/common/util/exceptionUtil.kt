package no.nav.personopplysninger.common.util

fun consumerErrorMessage(endpoint: String, status: Int, feilmelding: String): String {
    return "Feil i kall mot ekstern tjeneste - endepunkt=[$endpoint], HTTP response status=[$status], feilmelding=[$feilmelding]"
}