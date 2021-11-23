package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.tps.person.Informasjon_om_person_utland
import no.nav.tps.person.Kode

object Informasjon_om_person_utlandObjectMother {
    val dummyValues = Informasjon_om_person_utland(
            datoFraOgMed = dummyDato,
            kilde = dummyKilde,
            familienavnFodt = "dummy familienavn født",
            farsFamilenavn = "dummy fars familienavn",
            farsFornavn = "dummy fars fornavn",
            foedested = "dummy fødested",
            fornavnFodt = "dummy fornavn født",
            idOff = "dummy idOff",
            institusjon = "dummy institusjon",
            institusjonNavn = "dummy institusjonsnavn",
            kildePin = "dummy kildePin",
            land = dummyKode,
            morsFamilenavn = "dummy mors familienavn",
            morsFornavn = "dummy mors fornavn",
            nasjonalId = "dummy nasjonalId",
            nasjonalitet = dummyKode,
            sedRef = "dummy sedRef",
            sektor = dummyKode
    )

    val arrayOfDummyValues = arrayOf(
            dummyValues,
            Informasjon_om_person_utland(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    familienavnFodt = "dummy familienavn født 2",
                    farsFamilenavn = "dummy fars familienavn 2",
                    farsFornavn = "dummy fars fornavn 2",
                    foedested = "dummy fødested 2",
                    fornavnFodt = "dummy fornavn født 2",
                    idOff = "dummy idOff 2",
                    institusjon = "dummy institusjon 2",
                    institusjonNavn = "dummy institusjonsnavn 2",
                    kildePin = "dummy kildePin 2",
                    land = Kode(),
                    morsFamilenavn = "dummy mors familienavn 2",
                    morsFornavn = "dummy mors fornavn 2",
                    nasjonalId = "dummy nasjonalId 2",
                    nasjonalitet = dummyKode,
                    sedRef = "dummy sedRef 2",
                    sektor = dummyKode
            ))

}
