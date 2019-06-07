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
                publikumsmottak.aapningMandag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == mandag})
                publikumsmottak.aapningTirsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == tirsdag})
                publikumsmottak.aapningOnsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == onsdag})
                publikumsmottak.aapningTorsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == torsdag})
                publikumsmottak.aapningFredag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find {it.dag == fredag})

                mottaksliste.add(publikumsmottak)
            }


        return mottaksliste;
    }
}