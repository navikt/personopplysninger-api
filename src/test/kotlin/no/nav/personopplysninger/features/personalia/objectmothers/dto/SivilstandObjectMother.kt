package no.nav.personopplysninger.features.personalia.objectmothers.dto

import no.nav.personopplysninger.features.personalia.model.dto.Sivilstand

object SivilstandObjectMother{

    fun getGift(): Sivilstand {
        return Sivilstand(kode = "GIFT")
    }

    fun getUgift(): Sivilstand {
        return Sivilstand(kode = "UGIFT")
    }
}
