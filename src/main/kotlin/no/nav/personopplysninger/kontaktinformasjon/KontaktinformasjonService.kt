package no.nav.personopplysninger.kontaktinformasjon

import no.nav.personopplysninger.consumer.digdirkrr.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.transformer.KontaktinformasjonTransformer

class KontaktinformasjonService(
    private val kontaktinfoConsumer: KontaktinfoConsumer,
    private val kodeverkService: KodeverkService,
) {
    suspend fun hentKontaktinformasjon(token: String, fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(token, fodselsnr)
        val spraakTerm = kodeverkService.hentSpraak().term(inbound.spraak?.uppercase())
        return KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)
    }
}

