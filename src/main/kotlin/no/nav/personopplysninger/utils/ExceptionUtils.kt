package no.nav.personopplysninger.utils

fun consumerErrorMessage(endpoint: String, status: Int, feilmelding: String): String {
    return "Feil i kall mot ekstern tjeneste - endepunkt=[$endpoint], HTTP response status=[$status], feilmelding=[$feilmelding]"
}

fun consumerErrorMessage(endpoint: String, feilmelding: String): String {
    return "Feil i kall mot ekstern tjeneste - endepunkt=[$endpoint], feilmelding=[$feilmelding]"
}