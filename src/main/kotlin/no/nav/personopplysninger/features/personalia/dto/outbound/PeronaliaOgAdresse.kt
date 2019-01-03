package no.nav.personopplysninger.features.personalia.dto.outbound

data class PersonaliaOgAdresser(val personalia: Personalia, val adresser: Adresser?)

data class Personalia(
        val fornavn: String? = null,
        val etternavn: String? = null,
        val fnr: String? = null,
        val kontonr: String? = null,
        val tlfnr: Tlfnr? = null,
        val spraak: String? = null,
        val epostadr: String? = null,
        val personstatus: String? = null,
        val statsborgerskap: String? = null,
        val foedested: String? = null,
        val sivilstand: String? = null,
        val kjoenn: String? = null,
        val datakilder: Set<Kilde> // TODO Are: Må være annen type for å kunne serialiseres til JSON?
)

data class Adresser(
        val boadresse: Boadresse? = null,
        val geografiskTilknytning: GeografiskTilknytning? = null,
        val postadresse: Postadresse? = null,
        val prioritertAdresse: String? = null,
        val tilleggsadresse: Tilleggsadresse? = null,
        val utenlandskAdresse: UtenlandskAdresse? = null,
        val datakilder: Set<Kilde> // TODO Are: Må være annen type for å kunne serialiseres til JSON?
)