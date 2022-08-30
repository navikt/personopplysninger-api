package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.consumer.tpsproxy.dto.UtenlandskBank
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.UtenlandskBankInfo

object UtenlandskBankTransformer {
    fun toOutbound(inbound: UtenlandskBank, kodeverk: PersonaliaKodeverk): UtenlandskBankInfo {

        return UtenlandskBankInfo(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            bankkode = inbound.bankkode,
            banknavn = inbound.banknavn,
            iban = inbound.iban,
            kontonummer = inbound.kontonummer,
            swiftkode = inbound.swiftkode,
            land = inbound.land?.let { kodeverk.utenlandskbanklandterm },
            valuta = inbound.valuta?.let { kodeverk.utenlandskbankvalutaterm }
        )
    }
}