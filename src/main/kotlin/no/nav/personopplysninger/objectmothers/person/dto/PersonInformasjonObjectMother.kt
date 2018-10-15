package no.nav.personopplysninger.objectmothers.person.dto

import no.nav.personopplysninger.features.person.model.dto.PersonInformasjon

object PersonInformasjonObjectMother{
        fun getUngUgiftKvinne(): PersonInformasjon {
            return PersonInformasjon(
                    foedselsdato = "1991.01.01",
                    datoFraOgMed = "1991.01.01",
                    ident = "12345678911",
                    identtype = "fnr",
                    kjonn = "Kvinne",
                    navn = NavnObjectMother.getKvinne(),
                    telefon = TelefonObjectMother.getMobilOgJobb(),
                    sivilstand = SivilstandObjectMother.getUgift());
        }


}
