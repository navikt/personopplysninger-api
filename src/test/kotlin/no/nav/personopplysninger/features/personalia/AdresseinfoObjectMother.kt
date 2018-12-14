package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Adresseinfo

object AdresseinfoObjectMother {
    fun adresseinfoTestObject(): Adresseinfo {
        return Adresseinfo(
                boadresse = BoadresseObjectMother.vardeveien7(),
                postadresse = PostadresseObjectMother.testPostadresse(),
                utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse()
        )
    }

    fun adresseinfoNullObject(): Adresseinfo {
        return Adresseinfo(
                boadresse = null,
                postadresse = null,
                utenlandskAdresse = null
        )
    }
}