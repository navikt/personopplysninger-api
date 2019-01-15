package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.*

object PersoninfoObjectMother {
    val withValuesInAllFields: Personinfo = Personinfo(
            adresseinfo = AdresseinfoObjectMother.withValuesInAllFields,
            alder = 20,
            antallBarn = 2,
            antallLevendeBarnUnder18 = 2,
            kilde = dummyKilde,
            datoFraOgMed = dummyDato,
            brukerbehovListe = KodeMedDatoOgKildeObjectMother.arrayOfDummyValues,
            doedsdato = Doedsdato(
                    dato = dummyDato,
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde
            ),
            egenansatt = Egenansatt(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    erEgenansatt = true
            ),
            foedselsdato = dummyDato,
            foedtIKommune = dummyKode,
            foedtILand = dummyKode,
            foreldreansvar = KodeMedDatoOgKildeObjectMother.dummyValues,
            ident = "dummy ident",
            identtype = dummyKode,
            innvandringUtvandring = InnvandringUtvandringObjectMother.dummyValues,
            kjonn = Kjoennstype.M.name,
            kontonummer = Kontonummer(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    nummer = "dummy kontonummer"
            ),
            navn = NavnObjectMother.dummyValues,
            oppholdstillatelse = KodeMedDatoOgKildeObjectMother.dummyValues,
            relasjonFinnes = true,
            sivilstand = KodeMedDatoOgKildeObjectMother.dummyValues,
            spesiellOpplysning = KodeMedDatoOgKildeObjectMother.dummyValues,
            spraak = KodeMedDatoOgKildeObjectMother.dummyValues,
            statsborgerskap = KodeMedDatoOgKildeObjectMother.dummyValues,
            status = KodeMedDatoOgKildeObjectMother.dummyValues,
            telefon = TelefoninfoObjectMother.dummyValues,
            tiltak = Tiltak(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    datoTil = dummyDato,
                    type = dummyKode
            ),
            utenlandsinfoList = Informasjon_om_person_utlandObjectMother.arrayOfDummyValues,
            utenlandskBank = UtenlandskBankObjectMother.dummyValues,
            vergemaalListe = VergemaalObjectMother.arrayOfDummyValues
    )
}