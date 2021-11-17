package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import org.springframework.stereotype.Service

@Service
class PdlService(val pdlConsumer: PdlConsumer) {

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


    fun getPersonInfo(ident: String): PdlPerson {
        return pdlConsumer.getPersonInfo(ident).keepKontaktadressesWithPdlMasterOnly()
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