package no.nav.personopplysninger.features.endreopplysninger.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class EndringDto (
        val endringstype: String,
        val ident: String?,
        val innmeldtEndring: String?,
        val lineage: String?,
        val opplysningsId: String? = null,
        val opplysningstype: String?,
       //val opprettet: LocalDateTime?,
        //val sendt: Boolean?,
        val status: StatusDto
)
