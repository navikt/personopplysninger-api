package no.nav.personopplysninger

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.HttpClient
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import no.nav.personopplysninger.config.TestApplicationContext
import no.nav.personopplysninger.config.testModule

open class IntegrationTest {

    fun integrationTest(httpClient: HttpClient, block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = ApplicationConfig("application.conf")
        }
        application {
            testModule(TestApplicationContext(httpClient))
        }
        block()
    }

    suspend fun get(client: HttpClient, path: String): HttpResponse {
        val token = createAccessToken("12341234123")

        return client.get(path) {
            cookie("selvbetjening-idtoken", token)
        }
    }

    suspend fun post(client: HttpClient, path: String, body: Any? = null): HttpResponse {
        val token = createAccessToken("12341234123")

        return client.post(path) {
            cookie("selvbetjening-idtoken", token)
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    private fun createAccessToken(fnr: String): String {
        return JWT.create().withClaim("pid", fnr).sign(Algorithm.HMAC256("1"))
    }
}