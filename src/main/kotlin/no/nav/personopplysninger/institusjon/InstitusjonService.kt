package no.nav.personopplysninger.institusjon

import no.nav.personopplysninger.consumer.inst2.InstitusjonConsumer
import no.nav.personopplysninger.consumer.inst2.dto.InnsynInstitusjonsopphold

class InstitusjonService(private val institusjonConsumer: InstitusjonConsumer) {
    suspend fun hentInstitusjonsopphold(token: String, fnr: String): List<InnsynInstitusjonsopphold> {
        return institusjonConsumer.getInstitusjonsopphold(token, fnr)
    }
}
