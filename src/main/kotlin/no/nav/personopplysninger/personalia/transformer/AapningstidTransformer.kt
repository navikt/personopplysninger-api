package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.consumer.norg2.dto.Aapningstider
import no.nav.personopplysninger.personalia.dto.outbound.Aapningstid

object AapningstidTransformer {
    fun toOutbound(inbound: Aapningstider?): Aapningstid {
        return Aapningstid(
                dag = inbound?.dag,
                fra = inbound?.fra,
                til = inbound?.til,
                stengt = inbound?.stengt,
                kommentar = inbound?.kommentar
        )
    }
}
