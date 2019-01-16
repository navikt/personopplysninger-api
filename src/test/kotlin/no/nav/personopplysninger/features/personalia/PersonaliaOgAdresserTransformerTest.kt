package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.PersonaliaOgAdresserTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS


import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class PersonaliaOgAdresserTransformerTest {

    @Test
    fun gittPersonaliaOgAdresser_skalFaaPersnaliaOgAdresser() {
        val inbound = PersoninfoObjectMother.withValuesInAllFields

        val actual = PersonaliaOgAdresserTransformer.toOutbound(inbound)

        assertNotNull(actual.personalia)
        assertNotNull(actual.adresser)
    }

    @Test
    fun gittIngenAdresseinfio_skalIkkeFaaAdresser() {
        val inbound = PersoninfoObjectMother.withValuesInAllFields.copy(adresseinfo = null)

        val actual = PersonaliaOgAdresserTransformer.toOutbound(inbound)

        assertNotNull(actual.personalia)
        assertNull(actual.adresser)
    }

}