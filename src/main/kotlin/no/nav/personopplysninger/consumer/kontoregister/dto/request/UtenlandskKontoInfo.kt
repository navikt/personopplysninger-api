package no.nav.personopplysninger.consumer.kontoregister.dto.request

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.kontoregister.dto.response.UtenlandskKontoInformasjon

@Serializable
data class UtenlandskKontoInfo(
    val banknavn: String = "",
    val bankkode: String? = null,
    val bankLandkode: String = "",
    val valutakode: String,
    val swiftBicKode: String? = null,
    val bankadresse1: String = "",
    val bankadresse2: String = "",
    val bankadresse3: String = "",
) {
    companion object {
        fun from(utenlandskKontoInformasjon: UtenlandskKontoInformasjon): UtenlandskKontoInfo {
            return UtenlandskKontoInfo(
                banknavn = utenlandskKontoInformasjon.bank?.navn.orEmpty(),
                bankkode = utenlandskKontoInformasjon.bank?.kode,
                bankLandkode = utenlandskKontoInformasjon.landkode.orEmpty(),
                valutakode = utenlandskKontoInformasjon.valuta,
                swiftBicKode = utenlandskKontoInformasjon.swift,
                bankadresse1 = utenlandskKontoInformasjon.bank?.adresseLinje1.orEmpty(),
                bankadresse2 = utenlandskKontoInformasjon.bank?.adresseLinje2.orEmpty(),
                bankadresse3 = utenlandskKontoInformasjon.bank?.adresseLinje3.orEmpty(),
            )
        }
    }
}