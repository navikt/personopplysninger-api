package no.nav.personopplysninger

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.config.TestApplicationContext
import no.nav.personopplysninger.config.testModule
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import no.nav.personopplysninger.testutils.createAccessToken

open class IntegrationTest {

    fun integrationTest(httpClient: HttpClient, block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        application {
            testModule(TestApplicationContext(httpClient))
        }
        block()
    }

    fun ApplicationTestBuilder.httpClient() =
        createClient {
            install(ContentNegotiation) { json() }
            followRedirects = false
        }

    suspend fun get(
        client: HttpClient,
        path: String,
        cookie: Pair<String, String>? = null,
        queryParams: Map<String, String>? = null,
    ): HttpResponse {
        val token = createAccessToken()

        return client.get(path) {
            header("Authorization", "Bearer $token")
            cookie?.let {
                cookie(cookie.first, cookie.second)
            }
            queryParams?.forEach {
                parameter(it.key, it.value)
            }
        }
    }

    suspend fun post(client: HttpClient, path: String, body: Any? = null): HttpResponse {
        val token = createAccessToken()

        return client.post(path) {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)

            // Body castes til riktig klasse automagisk
            if (body is Telefonnummer) {
                setBody(body)
            } else if (body is Kontonummer) {
                setBody(body)
            } else if (body != null) {
                throw RuntimeException("Body er ugyldig. Legg inn en sjekk for klassen så den smart castes.")
            }
        }
    }
}