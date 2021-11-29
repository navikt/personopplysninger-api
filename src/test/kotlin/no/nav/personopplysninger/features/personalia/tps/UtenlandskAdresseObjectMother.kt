package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.UtenlandskAdresse

object UtenlandskAdresseObjectMother {
    fun utenlandskTestadresse(): UtenlandskAdresse {
        return UtenlandskAdresse(
                adresse1 = "Adresselinje 1",
                adresse2 = "Adresselinje 2",
                adresse3 = "adresselinje 3",
                datoFraOgMed = "dummy dato",
                datoTilOgMed = "dummy dato",
                land = "CPV"
        )
    }
}
