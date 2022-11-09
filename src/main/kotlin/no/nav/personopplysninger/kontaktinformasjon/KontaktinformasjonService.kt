package no.nav.personopplysninger.kontaktinformasjon

import no.nav.personopplysninger.common.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.kontaktinformasjon.consumer.KontaktinfoConsumer
import no.nav.personopplysninger.kontaktinformasjon.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.transformer.KontaktinformasjonTransformer

class KontaktinformasjonService(
    private var kontaktinfoConsumer: KontaktinfoConsumer,
    private var kodeverkService: KodeverkService,
) {
    suspend fun hentKontaktinformasjon(token: String, fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(token, fodselsnr)
        val spraakTerm = kodeverkService.hentSpraak().term(inbound.spraak?.uppercase())
        return KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)
    }
}

