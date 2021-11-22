package no.nav.personopplysninger.features.personalia.tps

import no.nav.personopplysninger.features.personalia.kodeverk.Sivilstand
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
            foedtILand = Kode("Landkoder", "NOR"),
            foreldreansvar = KodeMedDatoOgKildeObjectMother.dummyValues,
            ident = "dummy ident",
            identtype = dummyKode.copy(verdi = "DNR"),
            innvandringUtvandring = InnvandringUtvandringObjectMother.dummyValues,
            kjonn = null,
            kontonummer = Kontonummer(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    nummer = "dummy kontonummer"
            ),
            navn = NavnObjectMother.dummyValues,
            oppholdstillatelse = KodeMedDatoOgKildeObjectMother.dummyValues,
            relasjonFinnes = true,
            sivilstand = KodeMedDatoOgKildeObjectMother.medKode(Sivilstand.GIFT.name),
            spesiellOpplysning = KodeMedDatoOgKildeObjectMother.dummyValues,
            spraak = KodeMedDatoOgKildeObjectMother.dummyValues.copy(kode = Kode("Språk", null)),
            statsborgerskap = KodeMedDatoOgKildeObjectMother.dummyValues.copy(kode = Kode("StatsborgerskapFreg", "KOR")),
            telefon = TelefoninfoObjectMother.dummyValues,
            tiltak = Tiltak(
                    datoFraOgMed = dummyDato,
                    kilde = dummyKilde,
                    datoTil = dummyDato,
                    type = dummyKode
            ),
            utenlandsinfoList = Informasjon_om_person_utlandObjectMother.arrayOfDummyValues,
            utenlandskBank = UtenlandskBankObjectMother.utenlandskTestbank(),
            vergemaalListe = VergemaalObjectMother.arrayOfDummyValues
    )
}
