package no.nav.personopplysninger.consumer.pdl

import no.nav.pdl.generated.dto.HentPersonQuery

class PdlService(private val pdlConsumer: PdlConsumer) {
    suspend fun getOpplysningsIdForTelefon(
        token: String,
        ident: String,
        landskode: String,
        telefonnummer: String
    ): String? {
        return pdlConsumer.hentTelefon(token, ident).person?.telefonnummer
            ?.find { it.landskode == landskode && it.nummer == telefonnummer }
            ?.metadata
            ?.opplysningsId
    }

    suspend fun getOpplysningsIdForKontaktadresse(token: String, ident: String): String? {
        return pdlConsumer.hentKontaktadresse(token, ident).person
            ?.kontaktadresse
            ?.firstOrNull { it.metadata.master.equals(PDL, true) }
            ?.metadata
            ?.opplysningsId
    }

    suspend fun getPersonInfo(token: String, ident: String): HentPersonQuery.Result {
        return pdlConsumer.hentPerson(token, ident)
    }

    companion object {
        private const val PDL = "pdl"
    }
}