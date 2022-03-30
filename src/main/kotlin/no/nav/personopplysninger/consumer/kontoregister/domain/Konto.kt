package no.nav.personopplysninger.consumer.kontoregister.domain

data class Konto(
    val kontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo?
)
