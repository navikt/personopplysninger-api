package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable


@Serializable
data class UtenlandskBankInfo(

        val adresse1: String? = null,
        val adresse2: String? = null,
        val adresse3: String? = null,
        val bankkode: String? = null,
        val banknavn: String? = null,
        val iban: String? = null,
        val kontonummer: String? = null,
        val land: String? = null,
        val swiftkode: String? = null,
        val valuta: String? = null
)