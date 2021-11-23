package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.tps.person.Matrikkeladresse

object MatrikkeladresseObjectMother {
    fun vardeveien7(): Matrikkeladresse {
        return Matrikkeladresse(bruksnummer = "5002", festenummer = "34", gaardsnummer = "Vardeveien 7", undernummer = "23")
    }

    fun matrikkeladresseNullObject(): Matrikkeladresse {
        return Matrikkeladresse(bruksnummer = null, festenummer = null, gaardsnummer = null, undernummer = null)
    }
}
