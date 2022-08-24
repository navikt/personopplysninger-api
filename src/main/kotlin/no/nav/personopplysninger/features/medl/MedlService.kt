package no.nav.personopplysninger.features.medl

import no.nav.personopplysninger.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.medl.dto.Medlemskapsunntak

class MedlService(
    private var medlConsumer: MedlConsumer,
    private var kodeverkService: KodeverkService
) {
    suspend fun hentMeldemskap(token: String, fnr: String): Medlemskapsunntak {
        val perioder = medlConsumer.hentMedlemskap(token, fnr)
        return hentMedlemskapKodeverk(perioder)
    }

    private suspend fun hentMedlemskapKodeverk(inbound: Medlemskapsunntak): Medlemskapsunntak {
        val perioderMedKodeverk = inbound.apply {
            perioder = inbound.perioder.map { periode ->
                periode.apply {
                    hjemmel = kodeverkService.hentGrunnlagMedl().tekst(periode.hjemmel)
                    trygdedekning = kodeverkService.hentDekningMedl().tekst(periode.trygdedekning)
                    lovvalgsland = kodeverkService.hentLandKoder().term(periode.lovvalgsland)
                    studieinformasjon = periode.studieinformasjon?.apply {
                        statsborgerland =
                            kodeverkService.hentLandKoder().term(periode.studieinformasjon?.statsborgerland)
                        studieland = kodeverkService.hentLandKoder().term(periode.studieinformasjon?.studieland)
                    }
                }
            }
        }
        return perioderMedKodeverk
    }
}

