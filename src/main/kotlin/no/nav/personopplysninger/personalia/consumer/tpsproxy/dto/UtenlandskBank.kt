package no.nav.personopplysninger.personalia.consumer.tpsproxy.dto

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandskBank (
    val adresse1: String? = null,
    val adresse2: String? = null,
    val adresse3: String? = null,
    val bankkode: String? = null,
    val banknavn: String? = null,
    val datoFraOgMed: String? = null,
    val iban: String? = null,
    val kilde: String? = null,
    val kontonummer: String? = null,
    val land: Kode? = null,
    val swiftkode: String? = null,
    val valuta: Kode? = null
)