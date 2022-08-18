package no.nav.personopplysninger.features.personalia.dto.outbound

import kotlinx.serialization.Serializable

/**
 * @param verdi Personidenten, som kan være fnr eller D-nummer, og i teorien potensielt andre identtyper
 * @param type Personidenttypen, i form av koden i kodeverket for Identtype (FNR, DNR, BOST, m.m.)
 */
@Serializable
data class Personident(val verdi: String, val type: String?)