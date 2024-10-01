package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo

fun Konto.toOutbound(kodeverk: PersonaliaKodeverk): UtenlandskBankInfo {
    requireNotNull(utenlandskKontoInfo)

    return UtenlandskBankInfo(
        adresse1 = utenlandskKontoInfo.bankadresse1,
        adresse2 = utenlandskKontoInfo.bankadresse2,
        adresse3 = utenlandskKontoInfo.bankadresse3,
        bankkode = utenlandskKontoInfo.bankkode,
        banknavn = utenlandskKontoInfo.banknavn,
        kontonummer = kontonummer,
        swiftkode = utenlandskKontoInfo.swiftBicKode,
        land = kodeverk.utenlandskbanklandterm,
        valuta = kodeverk.utenlandskbankvalutaterm,
    )
}