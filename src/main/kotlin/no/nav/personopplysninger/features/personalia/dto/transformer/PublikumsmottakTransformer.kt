package no.nav.personopplysninger.features.personalia.dto.transformer


import no.nav.personopplysninger.consumer.norg2.domain.Publikumsmottak
import no.nav.personopplysninger.features.personalia.dto.outbound.Aapningstid
import no.nav.personopplysninger.features.personalia.dto.outbound.PublikumsmottakDto

object PublikumsmottakTransformer {
    private const val mandag = "Mandag"
    private const val tirsdag = "Tirsdag"
    private const val onsdag = "Onsdag"
    private const val torsdag = "Torsdag"
    private const val fredag = "Fredag"

    fun toOutbound(inbound: List<Publikumsmottak>?): ArrayList<PublikumsmottakDto> {

        val mottaksliste = ArrayList<PublikumsmottakDto>(0)

        for (mottak in inbound.orEmpty()) {
            val publikumsmottak = PublikumsmottakDto()
            val aapningstidsliste = ArrayList<Aapningstid>(0)
            publikumsmottak.gateadresse = mottak.besoeksadresse?.gatenavn
            publikumsmottak.poststed = mottak.besoeksadresse?.poststed
            publikumsmottak.husnummer = mottak.besoeksadresse?.husnummer
            publikumsmottak.husbokstav = mottak.besoeksadresse?.husbokstav
            publikumsmottak.postnummer = mottak.besoeksadresse?.postnummer
            publikumsmottak.stedsbeskrivelse = mottak.stedsbeskrivelse
            publikumsmottak.aapningMandag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find { it.dag == mandag })
            publikumsmottak.aapningTirsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find { it.dag == tirsdag })
            publikumsmottak.aapningOnsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find { it.dag == onsdag })
            publikumsmottak.aapningTorsdag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find { it.dag == torsdag })
            publikumsmottak.aapningFredag = AapningstidTransformer.toOutbound(mottak.aapningstider?.find { it.dag == fredag })
            for (aapningstider in mottak.aapningstider.orEmpty()) {
                if (aapningstider.dag != mandag && aapningstider.dag != tirsdag && aapningstider.dag != onsdag && aapningstider.dag != torsdag && aapningstider.dag != fredag) {
                    val aapningstid = AapningstidTransformer.toOutbound(aapningstider)
                    aapningstidsliste.add(aapningstid)
                }
            }
            mottaksliste.add(publikumsmottak)
        }
        return mottaksliste
    }
}
