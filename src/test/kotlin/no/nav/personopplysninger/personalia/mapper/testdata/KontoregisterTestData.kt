package no.nav.personopplysninger.personalia.mapper.testdata

import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.consumer.kontoregister.dto.request.UtenlandskKontoInfo

val defaultUtenlandskKontoInfo = UtenlandskKontoInfo(
    bankadresse1 = "Adresselinje 1",
    bankadresse2 = "Adresselinje 2",
    bankadresse3 = "Adresselinje 3",
    bankkode = "bankkode",
    banknavn = "banknavn",
    swiftBicKode = "swiftBicKode",
    bankLandkode = "bankLandkode",
    valutakode = "valutakode"
)

val defaultKonto = Konto(
    kontonummer = "dummyKontonummer",
    utenlandskKontoInfo = defaultUtenlandskKontoInfo,
)
