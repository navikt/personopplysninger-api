package no.nav.personopplysninger.testutils

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf

fun contentTypeJsonHeader() = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())