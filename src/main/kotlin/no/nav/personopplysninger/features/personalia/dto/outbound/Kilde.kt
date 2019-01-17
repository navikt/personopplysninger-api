package no.nav.personopplysninger.features.personalia.dto.outbound

/**
 * Navn på systemet som datumet kommer fra.
 */
data class Kilde(private val verdi: String) {

    fun asString() = verdi

    override fun toString(): String {
        return asString()
    }
}