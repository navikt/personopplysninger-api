package no.nav.personopplysninger.endreopplysninger.dto.inbound

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpphoerEndringsMelding(
    @SerialName("@type")
    override val subtype: String = "OPPHOER",
    override val kilde: String = "BRUKER SELV",
) : Endringsmelding