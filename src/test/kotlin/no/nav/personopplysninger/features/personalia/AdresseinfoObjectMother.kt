package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Adresseinfo

object AdresseinfoObjectMother {
    fun adresseinfoTest(): Adresseinfo {
        return Adresseinfo(
                boadresse = BoadresseObjectMother.vardeveien7(),
                postadresse = PostadresseObjectMother.testPostadresse(),
                utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse()
        )
    }
}