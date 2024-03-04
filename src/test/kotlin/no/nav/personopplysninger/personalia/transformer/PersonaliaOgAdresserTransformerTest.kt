package no.nav.personopplysninger.personalia.transformer


import kotlinx.serialization.json.JsonObject
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyKonto
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPdlData
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class PersonaliaOgAdresserTransformerTest {

    private val personaliaKodeverk = PersonaliaKodeverk()

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersonaliaOgAdresser() {
        val konto = createDummyKonto()
        val pdlData = createDummyPdlData()
        val actual = PersonaliaOgAdresserTransformer.toOutbound(
            pdlData,
            konto,
            personaliaKodeverk,
            JsonObject(emptyMap())
        )

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

}
