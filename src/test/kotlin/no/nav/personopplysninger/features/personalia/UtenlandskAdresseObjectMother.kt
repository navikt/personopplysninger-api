package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.UtenlandskAdresse

object UtenlandskAdresseObjectMother {
    fun utenlandskTestadresse(): UtenlandskAdresse {
        return UtenlandskAdresse(
                adresse1 = "Adresselinje 1",
                adresse2 = "Adresselinje 2",
                adresse3 = "adresselinje 3",
                land = "Utlandet"
        )
    }

    fun utenslandskAdresseNullObject(): UtenlandskAdresse {
        return UtenlandskAdresse(
                adresse1 = null,
                adresse2 = null,
                adresse3 = null,
                land = null
        )
    }
}