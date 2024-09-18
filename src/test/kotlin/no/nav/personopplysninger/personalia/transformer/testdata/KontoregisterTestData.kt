package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.consumer.kontoregister.dto.request.UtenlandskKontoInfo

val defaultUtenlandskKontoInfo = UtenlandskKontoInfo(
    bankadresse1 = "Adresselinje 1",
    bankadresse2 = "Adresselinje 2",
    bankadresse3 = "adresselinje 3",
    bankkode = "ssss",
    banknavn = "xxxx",
    swiftBicKode = "gdgf",
    bankLandkode = "sdf",
    valutakode = "asdf"
)

val defaultKonto = Konto(
    kontonummer = "dummyKontonummer",
    utenlandskKontoInfo = defaultUtenlandskKontoInfo,
)
