package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class StatusDto (
        val endringId: Int,
        val statusType: String
){
    fun isPending(): Boolean {
        return statusType.equals("PENDING")
    }
}