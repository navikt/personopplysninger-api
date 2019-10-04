package no.nav.personopplysninger.features.personalia.dto

import com.fasterxml.jackson.databind.ObjectMapper

fun getJson(dto: Any): String {
    return ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dto)
}