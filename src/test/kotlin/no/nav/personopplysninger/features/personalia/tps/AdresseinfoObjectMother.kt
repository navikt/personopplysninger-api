package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.Adresseinfo

object AdresseinfoObjectMother {
    val withValuesInAllFields = Adresseinfo(
            boadresse = BoadresseObjectMother.vardeveien7(),
            postadresse = PostadresseObjectMother.testPostadresse(),
            utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse(),
            geografiskTilknytning = GeografiskTilknytningObjectMother.withValuesInAllFields,
    )

    val adresseinfoNullObject = Adresseinfo(
            boadresse = null,
            postadresse = null,
            utenlandskAdresse = null,
            geografiskTilknytning = null,
            tilleggsadresse = null
    )

}
