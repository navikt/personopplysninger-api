package no.nav.personopplysninger.features.personalia.dto.outbound

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Oppholdsadresse

data class PersonaliaOgAdresser(val personalia: Personalia, val adresser: Adresser?, val enhetKontaktInformasjon: EnhetsKontaktInformasjon)

data class Personalia(
        val fornavn: String? = null,
        val etternavn: String? = null,
        val personident: Personident? = null,
        val kontonr: String? = null,
        val tlfnr: Tlfnr? = null,
        val utenlandskbank : UtenlandskBankInfo? = null,
        val statsborgerskap: String? = null,
        val foedested: String? = null,
        val sivilstand: String? = null,
        val kjoenn: String? = null
)

data class Adresser(
        val geografiskTilknytning: GeografiskTilknytning? = null,
        val kontaktadresser: List<Kontaktadresse> = arrayListOf(),
        val bostedsadresse: Bostedsadresse? = null,
        val oppholdsadresser: List<Oppholdsadresse> = arrayListOf(),
        val deltBosted: DeltBosted? = null,
)

data class EnhetsKontaktInformasjon(
        var enhet: GeografiskEnhetKontaktInformasjon? = null

)