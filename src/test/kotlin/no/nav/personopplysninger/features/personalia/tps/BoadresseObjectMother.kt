package no.nav.personopplysninger.features.personalia.tps

import no.nav.tps.person.Boadresse

object BoadresseObjectMother {
    fun vardeveien7(): Boadresse {
        return Boadresse(
                adresse = "Vardeveien 7",
                adressetillegg = "dummy adressetillegg",
                bydel = "dummy bydel",
                datoFraOgMed = "dummy dato",
                kommune = "0911",
                landkode = "NOR",
                matrikkeladresse = MatrikkeladresseObjectMother.vardeveien7(),
                postnummer = "2974",
                veiadresse = VeiadresseObjectMother.vardeveien7()
        )
    }

    fun boadresseNullObject(): Boadresse {
        return Boadresse(
                adresse = null,
                adressetillegg = null,
                bydel = null,
                datoFraOgMed = null,
                kommune = null,
                landkode = null,
                matrikkeladresse = null,
                postnummer = null,
                veiadresse = null
        )
    }
}