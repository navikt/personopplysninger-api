package no.nav.personopplysninger.consumer.pdl

import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import org.springframework.stereotype.Service

@Service
class PdlService(private val pdlConsumer: PdlConsumer) {
    fun getOpplysningsIdForTelefon(ident: String, landskode: String, telefonnummer: String): String? {
        return pdlConsumer.getTelefonInfo(ident).telefonnummer
            .find { tlf -> landskode == tlf.landskode && telefonnummer == tlf.nummer }
            ?.opplysningsId
    }

    fun getOpplysningsIdForKontaktadresse(ident: String): String? {
        return pdlConsumer.getKontaktadresseInfo(ident)
            .getKontaktadresseWithPdlMaster()
            ?.metadata
            ?.opplysningsId
    }

    fun getPersonInfo(ident: String): PdlData {
        return pdlConsumer.getPersonInfo(ident)
    }
}