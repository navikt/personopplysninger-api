package no.nav.personopplysninger.features.medl
import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MedlService @Autowired constructor(
        private var medlConsumer: MedlConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {
    fun hentMeldemskap(fnr: String): Medlemskapsunntak {
        val perioder = medlConsumer.hentMedlemskap(fnr);
        return hentMedlemskapKodeverk(perioder);
    }

    fun hentMedlemskapKodeverk(inbound: Medlemskapsunntak): Medlemskapsunntak {
        val perioderMedKodeverk = inbound.apply {
            perioder = inbound.perioder.map { periode ->
                periode.apply {
                    hjemmel = kodeverkConsumer.hentGrunnlagMedl().tekst(periode.hjemmel)
                    trygdedekning = kodeverkConsumer.hentDekningMedl().tekst(periode.trygdedekning)
                    lovvalgsland = kodeverkConsumer.hentLandKoder().term(periode.lovvalgsland)
                    studieinformasjon = periode.studieinformasjon?.apply {
                         statsborgerland = kodeverkConsumer.hentLandKoder().term(periode.studieinformasjon?.statsborgerland)
                         studieland = kodeverkConsumer.hentLandKoder().term(periode.studieinformasjon?.studieland)
                    }
                };
            };
        }
        return perioderMedKodeverk
    }
}

