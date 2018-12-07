package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.model.PersonaliaObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonaliaTransformerTest {

    private val personTransformer: PersonaliaTransformer = PersonaliaTransformer()

    @Test
    fun transformPersoninformasjonToPersonalia() {
        // TODO Are
//        assertEquals(PersonaliaObjectMother.kvinne, personTransformer.toInternal(PersonInformasjonObjectMother.ungUgiftKvinne))
    }
}


