package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Oppholdsadresse

@Serializable
data class PersonaliaOgAdresser(val personalia: Personalia, val adresser: Adresser?, val enhetKontaktInformasjon: EnhetsKontaktInformasjon)

@Serializable
data class Personalia(
        val fornavn: String? = null,
        val etternavn: String? = null,
        val personident: Personident? = null,
        val kontonr: String? = null,
        val tlfnr: Tlfnr? = null,
        val utenlandskbank : UtenlandskBankInfo? = null,
        val statsborgerskap: List<String> = arrayListOf(),
        val foedested: String? = null,
        val sivilstand: String? = null,
        val kjoenn: String? = null
)

@Serializable
data class Adresser(
        val geografiskTilknytning: GeografiskTilknytning? = null,
        val kontaktadresser: List<Kontaktadresse> = arrayListOf(),
        val bostedsadresse: Bostedsadresse? = null,
        val oppholdsadresser: List<Oppholdsadresse> = arrayListOf(),
        val deltBosted: DeltBosted? = null,
)

@Serializable
data class EnhetsKontaktInformasjon(
        var enhet: GeografiskEnhetKontaktInformasjon? = null

)