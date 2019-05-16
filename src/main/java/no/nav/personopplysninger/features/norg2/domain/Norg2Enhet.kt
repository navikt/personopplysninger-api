package no.nav.personopplysninger.features.norg2.domain


data class Norg2Enhet(
        val enhetId: kotlin.String? = null,
        val navn: kotlin.String? = null,
        val enhetNr: kotlin.String? = null,
        val antallRessurser: kotlin.String? = null,
        val status: kotlin.String? = null,
        val orgNivaa: kotlin.String? = null,
        val type: kotlin.String? = null,
        val organisasjonsnummer: kotlin.String? = null,
        val underEtableringDato: kotlin.String? = null,
        val aktiveringsdato: kotlin.String? = null,
        val underAvviklingDato: kotlin.String? = null,
        val nedleggelsesdato: kotlin.String? = null,
        val oppgavebehandler: kotlin.String? = null,
        val versjon: kotlin.String? = null,
        val sosialeTjenester: kotlin.String? = null,
        val kanalstrategi: kotlin.String? = null,
        val orgNrTilKommunaltNavKontor: kotlin.String? = null

)