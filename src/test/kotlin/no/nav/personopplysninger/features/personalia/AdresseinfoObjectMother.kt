package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Adresseinfo

object AdresseinfoObjectMother {
    val allFieldsHaveValues = Adresseinfo(
            boadresse = BoadresseObjectMother.vardeveien7(),
            postadresse = PostadresseObjectMother.testPostadresse(),
            utenlandskAdresse = UtenlandskAdresseObjectMother.utenlandskTestadresse(),
            geografiskTilknytning = GeografiskTilknytningObjectMother.allFieldsHaveValues,
            prioritertAdresse = KodeMedDatoOgKildeObjectMother.dummyValues.copy(kilde = "kilde for Adresseinfo.prioritertAdresse"),
            tilleggsadresse = TilleggsadresseObjectMother.allFieldsHaveValues
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