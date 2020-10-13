package no.nav.personopplysninger.features.medl

import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MedlService @Autowired constructor(
        private var medlConsumer: MedlConsumer
) {
    fun hentMeldemskap(fnr: String): List<Medlemskapsunntak> {
        return medlConsumer.hentMedlemskap(fnr);
    }
}

