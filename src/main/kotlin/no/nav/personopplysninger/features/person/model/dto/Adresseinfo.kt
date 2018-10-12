package no.nav.personopplysninger.features.person.model.dto

data class Adresseinfo(
        val boadresse: Boadresse,
        val geografiskTilknytning: GeografiskTilknytning,
        val postadresse: Postadresse,
        val prioritertAdresse: PrioritertAdresse,
        val tilleggsadresse: Tilleggsadresse,
        val utenlandskAdresse: UtenlandskAdresse
)
