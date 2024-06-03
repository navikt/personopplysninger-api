package no.nav.personopplysninger.medl

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.medl.dto.Medlemskapsunntak

class MedlService(
    private val medlConsumer: MedlConsumer,
    private val kodeverkConsumer: KodeverkConsumer
) {
    suspend fun hentMedlemskap(token: String, fnr: String): Medlemskapsunntak {
        val perioder = medlConsumer.hentMedlemskap(token, fnr)
        return hentMedlemskapKodeverk(perioder)
    }

    private suspend fun hentMedlemskapKodeverk(inbound: Medlemskapsunntak): Medlemskapsunntak {
        val perioderMedKodeverk = inbound.apply {
            perioder = inbound.perioder.map { periode ->
                periode.apply {
                    hjemmel = kodeverkConsumer.hentGrunnlagMedl().tekst(periode.hjemmel)
                    trygdedekning = periode.trygdedekning?.let { kodeverkConsumer.hentDekningMedl().tekst(it) }
                    lovvalgsland = periode.lovvalgsland?.let { kodeverkConsumer.hentLandKoder().term(it) }
                    studieinformasjon = periode.studieinformasjon?.apply {
                        statsborgerland = periode.studieinformasjon!!.statsborgerland.let {
                            kodeverkConsumer.hentLandKoder().term(it)
                        }
                        studieland = periode.studieinformasjon?.studieland?.let {
                            kodeverkConsumer.hentLandKoder().term(it)
                        }
                    }
                }
            }
        }
        return perioderMedKodeverk
    }
}

