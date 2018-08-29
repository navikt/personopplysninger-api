package no.nav.personopplysninger.objectmothers.person

import no.nav.personopplysninger.modell.person.Sivilstand

object SivilstandObjectMother{

    fun getGift(): Sivilstand{
        return Sivilstand(kode = "GIFT")
    }

    fun getUgift(): Sivilstand{
        return Sivilstand(kode = "UGIFT")
    }
}
