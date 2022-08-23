package no.nav.personopplysninger.features.kontaktinformasjon

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kontaktinformasjon.KontaktinfoConsumer
import no.nav.personopplysninger.features.kontaktinformasjon.dto.outbound.Kontaktinformasjon
import no.nav.personopplysninger.features.kontaktinformasjon.dto.transformer.KontaktinformasjonTransformer

class KontaktinformasjonService(
    private var kontaktinfoConsumer: KontaktinfoConsumer,
    private var kodeverkConsumer: KodeverkConsumer,
) {
    suspend fun hentKontaktinformasjon(token: String, fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(token, fodselsnr)
        val spraakTerm = kodeverkConsumer.hentSpraak().term(inbound.spraak?.uppercase())
        return KontaktinformasjonTransformer.toOutbound(inbound, spraakTerm)
    }
}

