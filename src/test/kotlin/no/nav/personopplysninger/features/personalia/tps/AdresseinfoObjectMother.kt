package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.Adresseinfo

object AdresseinfoObjectMother {
    val withValuesInAllFields = Adresseinfo(
            boadresse = BoadresseObjectMother.vardeveien7(),
            postadresse = PostadresseObjectMother.testPostadresse(),
            utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse(),
            geografiskTilknytning = GeografiskTilknytningObjectMother.withValuesInAllFields,
            prioritertAdresse = KodeMedDatoOgKildeObjectMother.dummyValues.copy(kilde = "kilde for Adresseinfo.prioritertAdresse")
    )

    val adresseinfoNullObject = Adresseinfo(
            boadresse = null,
            postadresse = null,
            utenlandskAdresse = null,
            geografiskTilknytning = null,
            prioritertAdresse = null,
            tilleggsadresse = null
    )

}
