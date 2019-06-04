package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.features.norg2.domain.Publikumsmottak
import no.nav.personopplysninger.features.personalia.dto.outbound.PublikumsmottakDto

object PublikumsmottakTransformer {
    fun toOutbound(inbound: List<Publikumsmottak>?) = PublikumsmottakDto(

            gateadresse = inbound.orEmpty()[0].besoeksadresse?.gatenavn,
            poststed = inbound.orEmpty()[0].besoeksadresse?.poststed,
            aapningmandag = inbound.orEmpty()[0].aapningstider?.let {AapningstidTransformer.toOutbound(inbound.orEmpty()[0].aapningstider!![0])},
            aapningtirsdag = inbound.orEmpty()[0].aapningstider?.let {AapningstidTransformer.toOutbound(inbound.orEmpty()[0].aapningstider!![1])},
            aapningonsdag = inbound.orEmpty()[0].aapningstider?.let {AapningstidTransformer.toOutbound(inbound.orEmpty()[0].aapningstider!![2])},
            aapningtorsdag = inbound.orEmpty()[0].aapningstider?.let {AapningstidTransformer.toOutbound(inbound.orEmpty()[0].aapningstider!![3])},
            aapningfredag = inbound.orEmpty()[0].aapningstider?.let {AapningstidTransformer.toOutbound(inbound.orEmpty()[0].aapningstider!![4])}
    )

}