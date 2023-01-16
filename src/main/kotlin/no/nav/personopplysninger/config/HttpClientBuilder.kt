package no.nav.personopplysninger.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json

object HttpClientBuilder {

    fun build(): HttpClient {
        return HttpClient(Apache) {
            defaultRequest {
                header("Nav-Consumer-Id", CONSUMER_ID)
            }
            install(ContentNegotiation) {
                json(jsonConfig())
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                retryOnException(maxRetries = 3, retryOnTimeout = true)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 3000
            }
            expectSuccess = false
        }
    }

}
