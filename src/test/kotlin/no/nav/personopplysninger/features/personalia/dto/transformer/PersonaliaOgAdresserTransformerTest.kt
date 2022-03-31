package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyKonto
import no.nav.personopplysninger.features.personalia.dto.transformer.testdata.createDummyPdlData
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
