package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Personinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertTrue

@TestInstance(PER_CLASS)
class PersonaliaTransformerTest {

    @Test
    fun gittPersonalia_skalFaaPersonalia() {
        val inbound: Personinfo = PersoninfoObjectMother.allFieldsHaveValues
//        assertTrue(false)
        // TODO Are
    }
}


