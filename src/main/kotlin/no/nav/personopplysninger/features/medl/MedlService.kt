package no.nav.personopplysninger.features.medl

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.medl.dto.Medlemskapsunntak

class MedlService(
    private var medlConsumer: MedlConsumer,
    private var kodeverkConsumer: KodeverkConsumer
) {
    suspend fun hentMeldemskap(token: String, fnr: String): Medlemskapsunntak {
        val perioder = medlConsumer.hentMedlemskap(token, fnr)
        return hentMedlemskapKodeverk(perioder)
    }

    private suspend fun hentMedlemskapKodeverk(inbound: Medlemskapsunntak): Medlemskapsunntak {
        val perioderMedKodeverk = inbound.apply {
            perioder = inbound.perioder.map { periode ->
                periode.apply {
                    hjemmel = kodeverkConsumer.hentGrunnlagMedl().tekst(periode.hjemmel)
                    trygdedekning = kodeverkConsumer.hentDekningMedl().tekst(periode.trygdedekning)
                    lovvalgsland = kodeverkConsumer.hentLandKoder().term(periode.lovvalgsland)
                    studieinformasjon = periode.studieinformasjon?.apply {
                        statsborgerland =
                            kodeverkConsumer.hentLandKoder().term(periode.studieinformasjon?.statsborgerland)
                        studieland = kodeverkConsumer.hentLandKoder().term(periode.studieinformasjon?.studieland)
                    }
                }
            }
        }
        return perioderMedKodeverk
    }
}

