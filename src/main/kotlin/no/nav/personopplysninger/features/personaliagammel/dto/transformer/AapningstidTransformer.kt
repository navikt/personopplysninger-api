package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.personopplysninger.features.personaliagammel.dto.outbound.Aapningstid
import no.nav.personopplysninger.oppslag.norg2.domain.Aapningstider

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
