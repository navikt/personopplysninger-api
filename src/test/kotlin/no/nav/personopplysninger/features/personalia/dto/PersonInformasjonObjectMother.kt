package no.nav.personopplysninger.features.personalia.dto

object PersonInformasjonObjectMother{
        val ungUgiftKvinne = PersonInformasjon(
                    foedselsdato = "1991.01.01",
                    datoFraOgMed = "1991.01.01",
                    ident = "12345678911",
                    identtype = "fnr",
                    kjonn = "Kvinne",
                    navn = NavnObjectMother.kvinne,
                    telefon = TelefonObjectMother.mobilOgJobb,
                    sivilstand = SivilstandObjectMother.ugift
        )
}
