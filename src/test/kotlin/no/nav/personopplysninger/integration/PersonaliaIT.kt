package no.nav.personopplysninger.integration

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import no.nav.personopplysninger.config.mainModule
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PersonaliaIT {

    @Test
    fun testytesty() = testApplication {
        application {
            mainModule()
        }
        val response = client.get("/personalia")
        assertEquals(HttpStatusCode.OK, response.status)
    }

}