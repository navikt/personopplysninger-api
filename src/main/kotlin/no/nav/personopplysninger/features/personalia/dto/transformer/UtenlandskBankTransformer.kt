package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.kontoregister.domain.Konto
import no.nav.personopplysninger.features.personalia.dto.outbound.UtenlandskBankInfo

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
                land = utenlandskKontoInfo.bankLandkode?.let { kodeverk.utenlandskbanklandterm },
                valuta = utenlandskKontoInfo.valutakode.let { kodeverk.utenlandskbankvalutaterm }
        )
    }
}