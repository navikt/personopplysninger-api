package no.nav.personopplysninger.features.personalia.dto.outbound

data class Navn(
        val fornavn: String? = null,
        val kilde: String? = null,
        val mellomnavn: String? = null,
        val slektsnavn: String? = null
)