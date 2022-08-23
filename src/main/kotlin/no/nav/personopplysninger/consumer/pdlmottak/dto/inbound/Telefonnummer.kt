package no.nav.personopplysninger.consumer.pdlmottak.dto.inbound

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Telefonnummer(
    @SerialName("@type")
    override val subtype: String = "TELEFONNUMMER",
    override val kilde: String = "BRUKER SELV",
    val landskode: String? = null,
    val nummer: String? = null,
    val prioritet: Int? = 1,
) : Endringsmelding