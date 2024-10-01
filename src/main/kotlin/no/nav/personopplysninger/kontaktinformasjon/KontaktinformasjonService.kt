package no.nav.personopplysninger.kontaktinformasjon

import no.nav.personopplysninger.consumer.digdirkrr.KontaktinfoConsumer
import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.kontaktinformasjon.dto.Kontaktinformasjon
import no.nav.personopplysninger.kontaktinformasjon.transformer.toOutbound

class KontaktinformasjonService(
    private val kontaktinfoConsumer: KontaktinfoConsumer,
    private val kodeverkConsumer: KodeverkConsumer,
) {
    suspend fun hentKontaktinformasjon(token: String, fodselsnr: String): Kontaktinformasjon {
        val inbound = kontaktinfoConsumer.hentKontaktinformasjon(token, fodselsnr)
        val spraakTerm = inbound.spraak?.uppercase()?.let { kodeverkConsumer.hentSpraak().term(it) }
        return inbound.toOutbound(spraakTerm)
    }
}

