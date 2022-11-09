package no.nav.personopplysninger.common.consumer.pdl

import no.nav.personopplysninger.common.consumer.pdl.dto.PdlData

class PdlService(private val pdlConsumer: PdlConsumer) {
    suspend fun getOpplysningsIdForTelefon(
        token: String,
        ident: String,
        landskode: String,
        telefonnummer: String
    ): String? {
        return pdlConsumer.getTelefonInfo(token, ident).telefonnummer
            .find { tlf -> landskode == tlf.landskode && telefonnummer == tlf.nummer }
            ?.metadata
            ?.opplysningsId
    }

    suspend fun getOpplysningsIdForKontaktadresse(token: String, ident: String): String? {
        return pdlConsumer.getKontaktadresseInfo(token, ident)
            .getKontaktadresseWithPdlMaster()
            ?.metadata
            ?.opplysningsId
    }

    suspend fun getPersonInfo(token: String, ident: String): PdlData {
        return pdlConsumer.getPersonInfo(token, ident)
    }
}