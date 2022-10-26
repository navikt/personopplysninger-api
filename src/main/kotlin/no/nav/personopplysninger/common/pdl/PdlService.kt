package no.nav.personopplysninger.common.pdl

import no.nav.personopplysninger.common.pdl.dto.PdlData
import org.slf4j.LoggerFactory

class PdlService(private val pdlConsumer: PdlConsumer) {

    private val log = LoggerFactory.getLogger(PdlService::class.java)

    suspend fun getOpplysningsIdForTelefon(
        token: String,
        ident: String,
        landskode: String,
        telefonnummer: String
    ): String? {

        log.info("Henter opplysningsId for telefonnummer med landskode $landskode")

        val pdlTelefonnummerList = pdlConsumer.getTelefonInfo(token, ident).telefonnummer;

        log.info("Fant totalt ${pdlTelefonnummerList.size} telefonnumre")

        val pdlTelefonnummer = pdlTelefonnummerList
            .find { tlf -> landskode == tlf.landskode && telefonnummer == tlf.nummer }

        if (pdlTelefonnummer == null) {
            log.warn("Fant ikke oppgitt telefonnummer")
            return null
        }

        if (pdlTelefonnummer.metadata.opplysningsId == null) {
            log.warn("Telefonnummer mangler opplysningsid")
            return null
        }

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