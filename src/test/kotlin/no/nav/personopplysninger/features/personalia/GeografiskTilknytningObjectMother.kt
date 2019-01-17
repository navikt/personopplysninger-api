package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.GeografiskTilknytning

object GeografiskTilknytningObjectMother {
    val withValuesInAllFields = GeografiskTilknytning(
            bydel = "dummy bydel",
            kilde = "kilde for GeografiskTilknytning",
            datoFraOgMed = "dummy dato",
            land = "dummy land",
            kommune = "dummy kommune"
    )

    val nullObject = GeografiskTilknytning(
            bydel = null,
            kilde = null,
            datoFraOgMed = null,
            land = null,
            kommune = null
    )


}
