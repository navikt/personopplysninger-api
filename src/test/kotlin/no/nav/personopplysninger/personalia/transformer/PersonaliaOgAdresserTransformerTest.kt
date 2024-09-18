package no.nav.personopplysninger.personalia.transformer


import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.transformer.testdata.defaultKonto
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPdlData
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class PersonaliaOgAdresserTransformerTest {

    private val personaliaKodeverk = PersonaliaKodeverk()

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersonaliaOgAdresser() {
        val konto = defaultKonto
        val pdlData = defaultPdlData
        val actual = PersonaliaOgAdresserTransformer.toOutbound(
            pdlData,
            konto,
            personaliaKodeverk,
            null
        )

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

}
