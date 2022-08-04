package no.nav.personopplysninger.consumer.kontoregister.dto

data class Konto(
    val kontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo?
)
