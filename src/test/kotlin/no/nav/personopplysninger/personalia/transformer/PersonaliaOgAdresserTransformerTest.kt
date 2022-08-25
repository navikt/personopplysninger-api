package no.nav.personopplysninger.personalia.transformer


import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyKonto
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPdlData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class PersonaliaOgAdresserTransformerTest {

    private val personaliaKodeverk = PersonaliaKodeverk()

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersonaliaOgAdresser() {
        val konto = createDummyKonto()
        val pdlData = createDummyPdlData()
        val actual = PersonaliaOgAdresserTransformer.toOutbound(pdlData, konto, personaliaKodeverk)

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

}
