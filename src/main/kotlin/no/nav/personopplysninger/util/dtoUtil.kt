package no.nav.personopplysninger.util

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun getJson(dto: Any): String {
    return jacksonObjectMapper().registerModule(JavaTimeModule()).writerWithDefaultPrettyPrinter()
        .writeValueAsString(dto)
}