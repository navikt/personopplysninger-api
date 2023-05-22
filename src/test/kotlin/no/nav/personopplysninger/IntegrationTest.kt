package no.nav.personopplysninger

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.config.TestApplicationContext
import no.nav.personopplysninger.config.testModule
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer

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

    suspend fun get(client: HttpClient, path: String): HttpResponse {
        val token = createAccessToken("12341234123")

        return client.get(path) {
            header("Authorization", "Bearer $token")
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

    private fun createAccessToken(fnr: String = "12341234123"): String {
        return JWT.create().withClaim("pid", fnr).sign(Algorithm.HMAC256("1"))
    }
}