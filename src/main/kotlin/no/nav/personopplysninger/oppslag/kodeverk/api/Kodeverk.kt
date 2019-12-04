package no.nav.personopplysninger.oppslag.kodeverk.api

data class Kodeverk(
        val koder: List<Kode>
) {
    private val koderByNavn: Map<String, Kode> by lazy {
        koder.map { it.navn to it}.toMap()
    }

    fun getBetydninger(kodeNavn: String): List<Betydning> {
        return koderByNavn[kodeNavn]?.betydninger ?: emptyList()
    }

    companion object {
        fun fromKoderBetydningerResponse(response: GetKodeverkKoderBetydningerResponse): Kodeverk {
            return response.betydninger
                    .entries
                    .map { Kode(it.key, it.value) }
                    .let { koder -> Kodeverk(koder) }
        }
    }
}
