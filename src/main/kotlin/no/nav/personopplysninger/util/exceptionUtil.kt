package no.nav.personopplysninger.util

import java.net.URI

fun consumerErrorMessage(endpoint: URI, status: Int, feilmelding: String): String {
    return "Feil i kall mot ekstern tjeneste - endepunkt=[$endpoint], HTTP response status=[$status], feilmelding=[$feilmelding]"
}