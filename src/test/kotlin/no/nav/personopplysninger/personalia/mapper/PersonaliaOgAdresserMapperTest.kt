package no.nav.personopplysninger.personalia.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldNotBeNull
import no.nav.personopplysninger.personalia.mapper.testdata.defaultEnhetKontaktinfo
import no.nav.personopplysninger.personalia.mapper.testdata.defaultKonto
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPdlData
import no.nav.personopplysninger.personalia.mapper.testdata.defaultPersonaliaKodeverk
import org.junit.jupiter.api.Test

class PersonaliaOgAdresserMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val outbound = defaultPdlData.toOutbound(
            defaultKonto,
            defaultPersonaliaKodeverk,
            defaultEnhetKontaktinfo
        )

        assertSoftly(outbound) {
            personalia.shouldNotBeNull()
            adresser.shouldNotBeNull()
            enhetKontaktInformasjon.shouldNotBeNull()
        }
    }
}
