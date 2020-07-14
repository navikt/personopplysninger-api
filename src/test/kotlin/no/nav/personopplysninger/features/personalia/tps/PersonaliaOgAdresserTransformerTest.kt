package no.nav.personopplysninger.features.personalia.tps


import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.personopplysninger.features.personalia.pdl.pdlPersonInfoWithValues
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class PersonaliaOgAdresserTransformerTest {

    private val personaliaKodeverk = PersonaliaKodeverk()

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersonaliaOgAdresser() {
        val inbound = PersoninfoObjectMother.withValuesInAllFields

        val pdlPersonInfo = pdlPersonInfoWithValues

        val actual = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlPersonInfo, personaliaKodeverk)

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

    @Test
    fun gittIngenAdresseinfio_skalIkkeFaaAdresser() {
        val inbound = PersoninfoObjectMother.withValuesInAllFields.copy(adresseinfo = null)

        val pdlPersonInfo = PdlPersonInfo()

        val actual = PersonaliaOgAdresserTransformer.toOutbound(inbound, pdlPersonInfo, personaliaKodeverk)

        assertNotNull(actual.personalia)
        assertNull(actual.adresser)
    }

}
