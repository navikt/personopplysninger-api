package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.objectmothers.PersonaliaObjectMother
import no.nav.personopplysninger.features.personalia.objectmothers.dto.PersonInformasjonObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonaliaTransformerTest {

    private val personTransformer: PersonaliaTransformer = PersonaliaTransformer()

    @Test
    fun transformPersoninformasjonToPersonalia() {
        assertEquals(PersonaliaObjectMother.getUngUgiftKvinne(), personTransformer.toInternal(PersonInformasjonObjectMother.getUngUgiftKvinne()))
    }
}


