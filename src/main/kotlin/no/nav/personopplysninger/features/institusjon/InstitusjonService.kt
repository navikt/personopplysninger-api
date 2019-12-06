package no.nav.personopplysninger.features.institusjon

import no.nav.personopplysninger.features.institusjon.domain.InnsynInstitusjonsopphold
import no.nav.personopplysninger.features.institusjon.domain.Institusjonstype
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InstitusjonService  @Autowired constructor(
    private var institusjonConsumer: InstitusjonConsumer
) {
    fun hentInstitusjonsopphold(fnr: String): List<InnsynInstitusjonsopphold> {
        return institusjonConsumer.getInstitusjonsopphold(fnr).filter {
            it.institusjonstype != Institusjonstype.FO
        }
    }
}
