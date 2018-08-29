package no.nav.personopplysninger.modell.person

data class UtenlandskBank(
        val adresse1: String,
        val adresse2: String,
        val adresse3: String,
        val bankkode: String,
        val banknavn: String,
        val datoFraOgMed: String,
        val iban: String,
        val kilde: String,
        val kontonummer: String,
        val land: String,
        val swiftkode: String,
        val valuta: String
)