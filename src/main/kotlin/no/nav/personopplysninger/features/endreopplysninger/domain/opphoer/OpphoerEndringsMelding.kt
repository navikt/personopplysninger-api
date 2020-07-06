package no.nav.personopplysninger.features.endreopplysninger.domain.opphoer

class OpphoerEndringsMelding private constructor() {
    val type = "OPPHOER"
    val kilde = "BRUKER SELV"

    companion object {
        val opphoerEndringsMelding = OpphoerEndringsMelding()
    }
}