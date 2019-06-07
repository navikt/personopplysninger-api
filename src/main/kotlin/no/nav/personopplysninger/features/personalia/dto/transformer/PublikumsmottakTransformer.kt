package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.features.norg2.domain.Publikumsmottak
import no.nav.personopplysninger.features.personalia.dto.outbound.PublikumsmottakDto

object PublikumsmottakTransformer {

    val mandag = "Mandag"
    val tirsdag = "Tirsdag"
    val onsdag = "Onsdag"
    val torsdag = "Torsdag"
    val fredag = "Fredag"

    fun toOutbound(inbound: List<Publikumsmottak>?) : ArrayList<PublikumsmottakDto>{

        var mottaksliste = ArrayList<PublikumsmottakDto>(0)

            for (mottak in inbound.orEmpty())
            {
               val publikumsmottak = PublikumsmottakDto()
                publikumsmottak.gateadresse = mottak.besoeksadresse?.gatenavn
                publikumsmottak.poststed = mottak.besoeksadresse?.poststed
                publikumsmottak.aapningmandag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == mandag})
                publikumsmottak.aapningtirsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == tirsdag})
                publikumsmottak.aapningonsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == onsdag})
                publikumsmottak.aapningtorsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == torsdag})
                publikumsmottak.aapningfredag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == fredag})

                mottaksliste.add(publikumsmottak)
            }


        return mottaksliste;
    }
}