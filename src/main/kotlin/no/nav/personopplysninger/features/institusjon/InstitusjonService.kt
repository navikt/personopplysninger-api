package no.nav.personopplysninger.features.institusjon

import no.nav.personopplysninger.features.institusjon.dto.InnsynInstitusjonsopphold
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InstitusjonService  @Autowired constructor(
    private var institusjonConsumer: InstitusjonConsumer
) {
    fun hentInstitusjonsopphold(fnr: String): List<InnsynInstitusjonsopphold> {
        return institusjonConsumer.getInstitusjonsopphold(fnr)
    }
}
