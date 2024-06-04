package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo

object UtenlandskBankTransformer {
    fun toOutbound(inbound: Konto, kodeverk: PersonaliaKodeverk): UtenlandskBankInfo {
        val utenlandskKontoInfo = inbound.utenlandskKontoInfo
            ?: throw IllegalStateException("Forsøkte å transformere ikke-eksisterende UtenlandskBankInfo-objekt")
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