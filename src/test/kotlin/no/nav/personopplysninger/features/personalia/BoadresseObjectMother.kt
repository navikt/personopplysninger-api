package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Boadresse

object BoadresseObjectMother {
    fun vardeveien7(): Boadresse {
        return Boadresse(
                adresse = "Vardeveien 7",
                kommune = "Kristiansand",
                matrikkeladresse = MatrikkeladresseObjectMother.vardeveien7(),
                postnummer = "5002",
                veiadresse = VeiadresseObjectMother.vardeveien7()
        )
    }
}