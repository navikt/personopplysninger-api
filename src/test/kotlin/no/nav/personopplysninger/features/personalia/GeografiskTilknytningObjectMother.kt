package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.GeografiskTilknytning

object GeografiskTilknytningObjectMother {
    val allFieldsHaveValues = GeografiskTilknytning(
            bydel = "dummy bydel",
            kilde = "kilde for GeografiskTilknytning",
            datoFraOgMed = "dummy dato",
            land = "dummy land",
            kommune = "dummy kommune"
    )

}
