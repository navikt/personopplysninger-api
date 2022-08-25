package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo

object UtenlandskBankTransformer {
    fun toOutbound(inbound: Konto, kodeverk: PersonaliaKodeverk): UtenlandskBankInfo {
        val utenlandskKontoInfo = inbound.utenlandskKontoInfo!!
        return UtenlandskBankInfo(
                adresse1 = utenlandskKontoInfo.bankadresse1,
                adresse2 = utenlandskKontoInfo.bankadresse2,
                adresse3 = utenlandskKontoInfo.bankadresse3,
                bankkode = utenlandskKontoInfo.bankkode,
                banknavn = utenlandskKontoInfo.banknavn,
                kontonummer = inbound.kontonummer,
                swiftkode = utenlandskKontoInfo.swiftBicKode,
                land = utenlandskKontoInfo.bankLandkode.let { kodeverk.utenlandskbanklandterm },
                valuta = utenlandskKontoInfo.valutakode.let { kodeverk.utenlandskbankvalutaterm }
        )
    }
}