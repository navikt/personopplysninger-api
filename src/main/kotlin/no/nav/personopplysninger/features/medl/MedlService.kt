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
        var perioder = medlConsumer.hentMedlemskap(fnr);
        return hentMedlemskapKodeverk(perioder);
    }

    fun hentMedlemskapKodeverk(inbound: Medlemskapsunntak): Medlemskapsunntak {
        val perioderMedKodeverk = inbound.apply {
            perioder = inbound.perioder.map { periode ->
                periode.apply {
                    hjemmel = "HJEMMEL_TEST"
                    trygdedekning = kodeverkConsumer.hentDekningMedl().term(periode.trygdedekning)
                };
            };
        }
        return perioderMedKodeverk
    }
}

