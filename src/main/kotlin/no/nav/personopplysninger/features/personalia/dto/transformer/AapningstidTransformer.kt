package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Aapningstider
import no.nav.personopplysninger.features.personalia.dto.outbound.Aapningstid
import no.nav.personopplysninger.features.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Adresseinfo

object AapningstidTransformer {
    fun toOutbound(inbound: Aapningstider?): Aapningstid {

        return Aapningstid(

                dag = inbound?.dag,
                fra = inbound?.fra,
                til = inbound?.til,
                stengt = inbound?.stengt
        )
    }

}