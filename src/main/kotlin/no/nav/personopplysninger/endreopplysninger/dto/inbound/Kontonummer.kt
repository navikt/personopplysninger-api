package no.nav.personopplysninger.endreopplysninger.dto.inbound

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kontonummer(
    @SerialName("@type")
    override val subtype: String = "KONTONUMMER",
    override val kilde: String = "BRUKER SELV",
    val utenlandskKontoInformasjon: UtenlandskKontoInformasjon? = null,
    val value: String? = null
) : Endringsmelding