package no.nav.personopplysninger.modell.person.mock

import no.nav.personopplysninger.modell.person.Navn
import no.nav.personopplysninger.modell.person.PersonInformasjon
import no.nav.personopplysninger.modell.person.Sivilstand
import no.nav.personopplysninger.modell.person.Telefon

class PersonInformasjonMock{

    fun mockPersonInformasjon(): PersonInformasjon{

        val personinfo: PersonInformasjon = PersonInformasjon(
                foedselsdato = "1990.01.01",
                datoFraOgMed = "1990.01.01",
                ident = "12345678911",
                identtype = "fnr",
                kjonn = "Kvinne",
                navn = mockNavn(),
                sivilstand = mockSivilstand(),
                telefon = mockTelefon())

        return personinfo
    }

    fun mockNavn(): Navn{
        val navn: Navn = Navn(
                fornavn = "Prøve",
                mellomnavn = "",
                slektsnavn = "Kanin")
        return navn
    }

    fun mockTelefon(): Telefon{
        val telefon: Telefon = Telefon(
                jobb = "12345678",
                jobbDatoRegistrert = "2010.01.01",
                mobil = "87654321",
                mobilDatoRegistrert = "2012.01.01")
        return telefon
    }

    fun mockSivilstand(): Sivilstand{
        val sivilstand: Sivilstand = Sivilstand(
                datoFraOgMed = "1990.01.01",
                kode = "Ugift")
        return sivilstand
    }


}