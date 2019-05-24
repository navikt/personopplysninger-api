package no.nav.personopplysninger.features.arbeidsforhold


import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.EnkeltArbeidsforholdTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class EnkeltArbeidsforholdTransformerTest {

    @Test
    fun skalFaaArbeidsforhold() {
        val inbound: Arbeidsforhold = ArbeidsforholdObjectMother.withDummyValues
        val actual: ArbeidsforholdDto = EnkeltArbeidsforholdTransformer.toOutbound(inbound, "NAV")
        assertNotNull(actual)
        assertEquals(inbound.navArbeidsforholdId, actual.arbeidsforholdId)
        assertEquals(inbound.sistBekreftet, actual.sistBekreftet)
        assertEquals(inbound.type, actual.type)
        assertEquals(inbound.ansettelsesperiode?.periode?.fom, actual.ansettelsesPeriode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.periode?.tom, actual.ansettelsesPeriode?.periodeTil)

    }

}




