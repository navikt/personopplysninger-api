package no.nav.personopplysninger.consumer.norg2.dto

import kotlinx.serialization.Serializable

@Serializable
data class Norg2Enhet(
    val enhetId: String? = null,
    val navn: String? = null,
    val enhetNr: String = "",
    val antallRessurser: String? = null,
    val status: String? = null,
    val orgNivaa: String? = null,
    val type: String? = null,
    val organisasjonsnummer: String? = null,
    val underEtableringDato: String? = null,
    val aktiveringsdato: String? = null,
    val underAvviklingDato: String? = null,
    val nedleggelsesdato: String? = null,
    val oppgavebehandler: String? = null,
    val versjon: String? = null,
    val sosialeTjenester: String? = null,
    val kanalstrategi: String? = null,
    val orgNrTilKommunaltNavKontor: String? = null
)
