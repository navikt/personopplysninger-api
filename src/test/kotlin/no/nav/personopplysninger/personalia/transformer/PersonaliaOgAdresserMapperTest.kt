package no.nav.personopplysninger.personalia.transformer

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldNotBeNull
import no.nav.personopplysninger.personalia.transformer.testdata.defaultEnhetKontaktinfo
import no.nav.personopplysninger.personalia.transformer.testdata.defaultKonto
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPdlData
import no.nav.personopplysninger.personalia.transformer.testdata.defaultPersonaliaKodeverk
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
