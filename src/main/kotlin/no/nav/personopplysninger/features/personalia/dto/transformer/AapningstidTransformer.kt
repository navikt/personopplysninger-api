package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.norg2.domain.Aapningstider
import no.nav.personopplysninger.features.personalia.dto.outbound.Aapningstid

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
