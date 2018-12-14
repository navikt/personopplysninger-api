package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.UtenlandskAdresseTransformer
import no.nav.tps.person.UtenlandskAdresse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals

@TestInstance(PER_CLASS)
class UtenlandskAdresseTransformerTest {

    @Test
    fun gittUtenlandskAdresse_skalFaaUtenlandskAdresse() {
        val inbound = UtenlandskAdresse(
                adresse1 = "Adresselinje 1",
                adresse2 = "Adresselinje 2",
                adresse3 = "adresselinje 3",
                land = "Utlandet"
        )

        val actual = UtenlandskAdresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse1, actual.adresse1)
        assertEquals(inbound.adresse2, actual.adresse2)
        assertEquals(inbound.adresse3, actual.adresse3)
        assertEquals(inbound.land, actual.land)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = UtenlandskAdresse(
                adresse1 = null,
                adresse2 = null,
                adresse3 = null,
                land = null
        )

        val actual = UtenlandskAdresseTransformer.toOutbound(inbound)

        assertEquals(inbound.adresse1, actual.adresse1)
        assertEquals(inbound.adresse2, actual.adresse2)
        assertEquals(inbound.adresse3, actual.adresse3)
        assertEquals(inbound.land, actual.land)
    }
}