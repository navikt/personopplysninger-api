package no.nav.personopplysninger.personalia.transformer


import no.nav.personopplysninger.personalia.consumer.dto.Aapningstider
import no.nav.personopplysninger.personalia.consumer.dto.Publikumsmottak
import no.nav.personopplysninger.personalia.dto.outbound.Aapningstid
import no.nav.personopplysninger.personalia.dto.outbound.PublikumsmottakDto

object PublikumsmottakTransformer {
    private const val mandag = "Mandag"
    private const val tirsdag = "Tirsdag"
    private const val onsdag = "Onsdag"
    private const val torsdag = "Torsdag"
    private const val fredag = "Fredag"

    private val UKEDAGER = listOf("Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag")

    fun toOutbound(inbound: List<Publikumsmottak>?): List<PublikumsmottakDto> {

        return inbound?.map { mottak ->
            PublikumsmottakDto(
                gateadresse = mottak.besoeksadresse?.gatenavn,
                poststed = mottak.besoeksadresse?.poststed,
                husnummer = mottak.besoeksadresse?.husnummer,
                husbokstav = mottak.besoeksadresse?.husbokstav,
                postnummer = mottak.besoeksadresse?.postnummer,
                stedsbeskrivelse = mottak.stedsbeskrivelse,
                aapningstider = getAapningstider(mottak.aapningstider),
                spesielleAapningstider = getSpesielleAapningstider(mottak.aapningstider)
            )
        } ?: emptyList()
    }

    fun getAapningstider(aapningstider: List<Aapningstider>?): List<Aapningstid> {
        return UKEDAGER.map { AapningstidTransformer.toOutbound(aapningstider?.find { aapningstid -> aapningstid.dag == it }) }
    }

    fun getSpesielleAapningstider(aapningstider: List<Aapningstider>?): List<Aapningstid> {
        return aapningstider?.filter { it.dag == null }?.map { AapningstidTransformer.toOutbound(it) } ?: emptyList()
    }
}
