package no.nav.personopplysninger.features.institusjon.domain

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException


class InstitusjonstypeSerializer: JsonSerializer<Institusjonstype>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: Institusjonstype, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.tekst)
    }
}
