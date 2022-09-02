package no.nav.personopplysninger.personalia.transformer


import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPdlData
import no.nav.personopplysninger.personalia.transformer.testdata.createDummyPersonInfo
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class PersonaliaOgAdresserTransformerTest {

    private val personaliaKodeverk = PersonaliaKodeverk()

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersonaliaOgAdresser() {
        val inbound = createDummyPersonInfo()
        val pdlData = createDummyPdlData()
        val actual = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlData, personaliaKodeverk)

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

}
