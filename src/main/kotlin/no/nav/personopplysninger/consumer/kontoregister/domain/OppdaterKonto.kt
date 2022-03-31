package no.nav.personopplysninger.consumer.kontoregister.domain

data class OppdaterKonto(
    val kontohaver: String,
    val nyttKontonummer: String,
    val utenlandskKontoInfo: UtenlandskKontoInfo? = null
)
