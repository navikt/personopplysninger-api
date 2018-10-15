package no.nav.personopplysninger.features.personalia.objectmothers.dto

import no.nav.personopplysninger.features.personalia.model.dto.Navn

object NavnObjectMother{

    fun getGiftKvinne(): Navn {
        return Navn(fornavn = "Kari", slektsnavn = "Nordmann", slektsnavnUgift = "", kilde = "TEST")
    }

    fun getKvinne(): Navn {
        return Navn(fornavn = "Kari", slektsnavn = "Nordmann", kilde = "TEST")
    }

    fun getMann(): Navn {
        return Navn(fornavn = "Ola", slektsnavn = "Nordmann")
    }

}
