package no.nav.personopplysninger.features.kontaktinformasjon

import no.nav.personopplysninger.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.consumer.kontaktinformasjon.KontaktinfoConsumer
import no.nav.personopplysninger.features.kontaktinformasjon.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.kontaktinformasjon.dto.transformer.KontaktinformasjonTransformer

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

