package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Adresseinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {
    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = Adresseinfo()
    }
}