package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import org.springframework.stereotype.Service

@Service
class PdlService(val pdlConsumer: PdlConsumer) {

    fun getOpplysningsIdForTelefon(ident: String, landskode: String, telefonnummer: String): String? {
        return pdlConsumer.getTelefonInfo(ident).telefonnummer
                .find { tlf -> landskode == tlf.landskode && telefonnummer == tlf.nummer }
                ?.opplysningsId
    }


    fun getPersonInfo(ident: String): PdlPersonInfo {
        return pdlConsumer.getPersonInfo(ident)
    }
}