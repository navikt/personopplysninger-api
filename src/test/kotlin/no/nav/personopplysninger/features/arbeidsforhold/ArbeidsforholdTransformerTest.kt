package no.nav.personopplysninger.features.arbeidsforhold


import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.tps.person.Kode
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import no.nav.tps.person.Telefoninfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class ArbeidsforholdTransformerTest {

    @Test
    fun skalFaaArbeidsforhold() {
        val inbound: Arbeidsforhold = ArbeidsforholdObjectMother.withDummyValues
        val actual: ArbeidsforholdDto = ArbeidsforholdTransformer.toOutbound(inbound, "NAV")
        assertNotNull(actual)
        assertEquals(inbound.arbeidsforholdId, actual.arbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.periode?.fom, actual.ansettelsesPeriode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.periode?.tom, actual.ansettelsesPeriode?.periodeTil)
        assertEquals(inbound.permisjonPermitteringer?.size, actual.permisjonPermittering?.size)
        assertEquals(inbound.utenlandsopphold?.size, actual.utenlandsopphold?.size)
        assertEquals(inbound.sistBekreftet, actual.sistBekreftet)

    }

}




