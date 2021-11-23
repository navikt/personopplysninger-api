package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.tps.person.Postadresse

object PostadresseObjectMother {
    fun testPostadresse(): Postadresse {
        return Postadresse(
                adresse1 = "Adresselinje 1",
                adresse2 = "Adresselinje 2",
                adresse3 = "adresselinje 3",
                datoFraOgMed = "dummy dato",
                land ="NOR",
                postnummer = "2974"
        )
    }

    fun postadresseNullObject(): Postadresse {
        return Postadresse(
                adresse1 = null,
                adresse2 = null,
                adresse3 = null,
                land = null,
                postnummer = null
        )
    }
}
