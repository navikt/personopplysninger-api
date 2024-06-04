package no.nav.personopplysninger.personalia.dto.outbound

import kotlinx.serialization.Serializable
import no.nav.personopplysninger.consumer.norg2.dto.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Bostedsadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.DeltBosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Kontaktadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Oppholdsadresse

@Serializable
data class PersonaliaOgAdresser(
    val personalia: Personalia,
    val adresser: Adresser?,
    val enhetKontaktInformasjon: Norg2EnhetKontaktinfo?
)

@Serializable
data class Personalia(
    val fornavn: String? = null,
    val etternavn: String? = null,
    val personident: Personident? = null,
    val kontoregisterStatus: String = "SUCCESS",
    val kontonr: String? = null,
    val utenlandskbank: UtenlandskBankInfo? = null,
    val tlfnr: Tlfnr? = null,
    val statsborgerskap: List<String> = emptyList(),
    val foedested: String? = null,
    val sivilstand: String? = null,
    val kjoenn: String? = null
)

@Serializable
data class Adresser(
    val kontaktadresser: List<Kontaktadresse> = emptyList(),
    val bostedsadresse: Bostedsadresse? = null,
    val oppholdsadresser: List<Oppholdsadresse> = emptyList(),
    val deltBosted: DeltBosted? = null,
)