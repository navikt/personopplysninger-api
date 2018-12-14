package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.dto.transformer.AdresseinfoTransformer
import no.nav.tps.person.Adresseinfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(PER_CLASS)
class AdresseinfoTransformerTest {

    @Test
    fun gittAdresse_skalFaaAdresse() {
        val inbound = Adresseinfo(
                boadresse = BoadresseObjectMother.vardeveien7(),
                postadresse = PostadresseObjectMother.testPostadresse(),
                utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse()
        )

        val actual = AdresseinfoTransformer.toOutbound(inbound)

        assertNotNull(actual.boadresse)
        assertNotNull(actual.postadresse)
        assertNotNull(actual.utenlandskAdresse)
    }

    @Test
    fun gittNull_skalFaaNull() {
        val inbound = Adresseinfo(
                boadresse = null,
                postadresse = null,
                utenlandskAdresse = null
        )

        val actual = AdresseinfoTransformer.toOutbound(inbound)

        assertNull(actual.boadresse)
        assertNull(actual.postadresse)
        assertNull(actual.utenlandskAdresse)
    }
}