package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.UtenlandskBankInfo
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.UtenlandskBank

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
                land = inbound.land?.let { kodeverk.utenlandskbanklandterm } ?: run {""},
                valuta = inbound.valuta?.let { kodeverk.utenlandskbankvalutaterm } ?: run {""}
        )
    }
}