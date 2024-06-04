package no.nav.personopplysninger.medl

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.medl.MedlConsumer
import no.nav.personopplysninger.consumer.medl.dto.Medlemskapsperiode
import no.nav.personopplysninger.consumer.medl.dto.Medlemskapsunntak
import no.nav.personopplysninger.consumer.medl.dto.Studieinformasjon

class MedlService(
    private val medlConsumer: MedlConsumer,
    private val kodeverkConsumer: KodeverkConsumer
) {
    suspend fun hentMedlemskap(token: String, fnr: String): Medlemskapsunntak {
        val medlemskap = medlConsumer.hentMedlemskap(token, fnr)
        return Medlemskapsunntak(
            medlemskap.perioder.map { periode ->
                periode.copy(
                    hjemmel = periode.hjemmelKodeverk(),
                    trygdedekning = periode.trygdedekningKodeverk(),
                    lovvalgsland = periode.lovvalgslandKodeverk(),
                    studieinformasjon = periode.studieinformasjon?.copy(
                        statsborgerland = periode.studieinformasjon.statsborgerlandKodeverk(),
                        studieland = periode.studieinformasjon.studielandKodeverk()
                    )
                )
            }
        )
    }

    private suspend fun Medlemskapsperiode.trygdedekningKodeverk(): String? {
        return trygdedekning?.let { kodeverkConsumer.hentDekningMedl().tekst(it) }
    }

    private suspend fun Medlemskapsperiode.hjemmelKodeverk(): String {
        return kodeverkConsumer.hentGrunnlagMedl().tekst(hjemmel)
    }

    private suspend fun Medlemskapsperiode.lovvalgslandKodeverk(): String? {
        return lovvalgsland?.let { kodeverkConsumer.hentLandKoder().term(it) }
    }

    private suspend fun Studieinformasjon.statsborgerlandKodeverk(): String {
        return statsborgerland.let { kodeverkConsumer.hentLandKoder().term(it) }
    }

    private suspend fun Studieinformasjon.studielandKodeverk(): String? {
        return studieland?.let { kodeverkConsumer.hentLandKoder().term(it) }
    }
}

