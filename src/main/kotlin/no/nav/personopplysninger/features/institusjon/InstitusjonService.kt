package no.nav.personopplysninger.features.institusjon

import no.nav.personopplysninger.consumer.inst.InstitusjonConsumer
import no.nav.personopplysninger.consumer.inst.dto.InnsynInstitusjonsopphold

class InstitusjonService(private var institusjonConsumer: InstitusjonConsumer) {
    suspend fun hentInstitusjonsopphold(token: String, fnr: String): List<InnsynInstitusjonsopphold> {
        return institusjonConsumer.getInstitusjonsopphold(token, fnr)
    }
}
