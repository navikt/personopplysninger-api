package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
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
            .keepKontaktadressesWithPdlMasterOnly()
            .kontaktadresse
            .firstOrNull()
            ?.metadata
            ?.opplysningsId
    }


    fun getPersonInfo(ident: String): PdlData {
        return pdlConsumer.getPersonInfo(ident)
    }

    private fun PdlPerson.keepKontaktadressesWithPdlMasterOnly(): PdlPerson {
        val pdlMasterKontaktadresses = kontaktadresse
            .filter { adresse -> adresse.metadata.master.equals("pdl", true) }

        return PdlPerson(
            telefonnummer = this.telefonnummer,
            kontaktadresse = pdlMasterKontaktadresses
        )
    }
}