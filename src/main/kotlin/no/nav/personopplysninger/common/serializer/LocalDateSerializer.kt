package no.nav.personopplysninger.common.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateSerializer : KSerializer<LocalDate> {

    override fun deserialize(decoder: Decoder): LocalDate {
        val value = decoder.decodeString()
        return LocalDate.parse(value)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_DATE))
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
}
