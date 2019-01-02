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
        val datakilder: Set<Kilde>? = null
)

data class Adresser(
        val boadresse: Boadresse? = null,
        val geografiskTilknytning: GeografiskTilknytning? = null,
        val postadresse: Postadresse? = null,
        val prioritertAdresse: KodeMedDatoOgKilde? = null,
        val tilleggsadresse: Tilleggsadresse? = null,
        val utenlandskAdresse: UtenlandskAdresse? = null,
        val datakilder: MutableSet<Kilde> = mutableSetOf() // TODO Are: Må typen være Array<String> for at den skal kunne mappes til JSON?
)