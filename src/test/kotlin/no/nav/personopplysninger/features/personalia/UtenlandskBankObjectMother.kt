package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.UtenlandskBank

object UtenlandskBankObjectMother {
    val dummyValues = UtenlandskBank(
            adresse1 = dummyAdresse,
            land = dummyKode,
            kilde = dummyKilde,
            datoFraOgMed = dummyDato,
            kontonummer = "dummy kontonummer",
            adresse2 = dummyAdresse,
            adresse3 = dummyAdresse,
            bankkode = "dummy bankkode",
            banknavn = "dummy banknavn",
            iban = "dummy iban",
            swiftkode = "dummy swiftKode",
            valuta = dummyKode
    )

}
