package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlData
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPersonInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
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
