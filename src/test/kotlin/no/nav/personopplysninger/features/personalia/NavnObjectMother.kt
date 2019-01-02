package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Navn

object NavnObjectMother {
    val dummyValues = Navn(
            datoFraOgMed = dummyDato,
            kilde = dummyKilde,
            slektsnavn = "dummy slektsnavn",
            mellomnavn = "dummy melllomnavn",
            fornavn = "dummy fornavn",
            forkortetNavn = "dummy forkortet navn",
            slektsnavnUgift = "dummy slektsnavn ugift"
    )

}
