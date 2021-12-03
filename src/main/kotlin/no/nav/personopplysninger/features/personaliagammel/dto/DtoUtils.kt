package no.nav.personopplysninger.features.personaliagammel.dto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun getJson(dto: Any): String {
    return jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dto)
}
