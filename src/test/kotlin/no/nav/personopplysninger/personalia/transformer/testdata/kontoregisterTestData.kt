package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.personopplysninger.common.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.kontoregister.dto.outbound.UtenlandskKontoInfo


fun createDummyKonto(): Konto {
    return Konto(
        kontonummer = "dummyKontonummer",
        utenlandskKontoInfo = createDummyUtenlandskKontoInfo(),
    )
}


fun createDummyUtenlandskKontoInfo(): UtenlandskKontoInfo {
    return UtenlandskKontoInfo(
        bankadresse1 = "Adresselinje 1",
        bankadresse2 = "Adresselinje 2",
        bankadresse3 = "adresselinje 3",
        bankkode = "ssss",
        banknavn = "xxxx",
        swiftBicKode = "gdgf",
        bankLandkode = "sdf",
        valutakode = "asdf"
    )
}