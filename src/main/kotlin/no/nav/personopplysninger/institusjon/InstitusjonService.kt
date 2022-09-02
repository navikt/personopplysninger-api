package no.nav.personopplysninger.institusjon

import no.nav.personopplysninger.institusjon.consumer.InstitusjonConsumer
import no.nav.personopplysninger.institusjon.dto.InnsynInstitusjonsopphold

class InstitusjonService(private var institusjonConsumer: InstitusjonConsumer) {
    suspend fun hentInstitusjonsopphold(token: String, fnr: String): List<InnsynInstitusjonsopphold> {
        return institusjonConsumer.getInstitusjonsopphold(token, fnr)
    }
}
