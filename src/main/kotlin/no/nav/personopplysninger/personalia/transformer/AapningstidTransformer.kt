package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.consumer.dto.Aapningstider
import no.nav.personopplysninger.personalia.dto.outbound.Aapningstid

object AapningstidTransformer {
    fun toOutbound(inbound: Aapningstider?): Aapningstid {
        return Aapningstid(
            dag = inbound?.dag,
            dato = inbound?.dato,
            fra = inbound?.fra,
            til = inbound?.til,
            stengt = inbound?.stengt,
            kommentar = inbound?.kommentar
        )
    }
}
