package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.tps.person.Kode
import no.nav.tps.person.Kontonummer
import no.nav.tps.person.Personinfo
import no.nav.tps.person.UtenlandskBank


fun createDummyPersonInfo(): Personinfo {
    return Personinfo(
        kontonummer = Kontonummer(
            datoFraOgMed = "dummyDato",
            kilde = "dummyKilde",
            nummer = "dummyKontonummer"
        ),
        utenlandskBank = createDummyUtenlandskBank(),
    )
}


fun createDummyUtenlandskBank(): UtenlandskBank {
    return UtenlandskBank(
        adresse1 = "Adresselinje 1",
        adresse2 = "Adresselinje 2",
        adresse3 = "adresselinje 3",
        bankkode = "ssss",
        banknavn = "xxxx",
        iban = "aaaa",
        kontonummer = "045654656546",
        swiftkode = "gdgf",
        land = createDummyKode(),
        valuta = createDummyKode()
    )
}

fun createUtenlandskBankNullObject() = UtenlandskBank(
    adresse1 = null,
    land = null,
    kilde = null,
    datoFraOgMed = null,
    kontonummer = null,
    adresse2 = null,
    adresse3 = null,
    bankkode = null,
    banknavn = null,
    iban = null,
    swiftkode = null,
    valuta = null
)

private fun createDummyKode(): Kode {
    return Kode("dummy kodeverk", "dummy verdi")
}