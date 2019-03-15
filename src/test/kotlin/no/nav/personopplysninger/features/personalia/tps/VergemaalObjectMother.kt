package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.Vergemaal

object VergemaalObjectMother {
    val dummyValues = Vergemaal(
            datoFraOgMed = dummyDato,
            kilde = dummyKilde,
            type = dummyKode,
            forkortetNavn = "dummy forkortet navn",
            egenansatt = true,
            embete = dummyKode,
            fnr = "dummy fnr",
            id = "dummy id",
            mandattype = dummyKode,
            saksId = "dummy saksId",
            sakstype = dummyKode,
            spesreg = dummyKode,
            vedtaksdato = dummyDato
    )

    val arrayOfDummyValues = arrayOf(
            dummyValues,
            Vergemaal(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    type = dummyKode,
                    forkortetNavn = "dummy forkortet navn 2",
                    egenansatt = true,
                    embete = dummyKode,
                    fnr = "dummy fnr 2",
                    id = "dummy id 2",
                    mandattype = dummyKode,
                    saksId = "dummy saksId 2",
                    sakstype = dummyKode,
                    spesreg = dummyKode,
                    vedtaksdato = dummyDato
            )
    )

}
