package no.nav.personopplysninger.objectmothers.person

import no.nav.personopplysninger.modell.person.PersonInformasjon
import no.nav.personopplysninger.modell.person.Telefon
import no.nav.personopplysninger.objectmothers.person.NavnObjectMother

object PersonInformasjonObjectMother{
        fun getUngUgiftKvinne(): PersonInformasjon{
            return PersonInformasjon(
                    foedselsdato = "1991.01.01",
                    datoFraOgMed = "1991.01.01",
                    ident = "12345678911",
                    identtype = "fnr",
                    kjonn = "Kvinne",
                    navn =  NavnObjectMother.getKvinne(),
                    telefon = TelefonObjectMother.getMobilOgJobb(),
                    sivilstand = SivilstandObjectMother.getUgift());
        }


}
