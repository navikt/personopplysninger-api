package no.nav.personopplysninger.common.kodeverk

import com.github.benmanes.caffeine.cache.Cache
import kotlinx.coroutines.runBlocking
import no.nav.personopplysninger.common.kodeverk.dto.Kodeverk

class KodeverkService(
    private val cache: Cache<String, Kodeverk>,
    private val kodeverkConsumer: KodeverkConsumer
) {
    suspend fun hentRetningsnumre(): Kodeverk {
        return hentKodeverkBetydning("Retningsnumre", true)
    }

    suspend fun hentKommuner(): Kodeverk {
        return hentKodeverkBetydning("Kommuner", false)
    }

    suspend fun hentLandKoder(): Kodeverk {
        return hentKodeverkBetydning("Landkoder", true)
    }

    suspend fun hentLandKoderISO2(): Kodeverk {
        return hentKodeverkBetydning("LandkoderISO2", true)
    }

    suspend fun hentPostnummer(): Kodeverk {
        return hentKodeverkBetydning("Postnummer", true)
    }

    suspend fun hentValuta(): Kodeverk {
        return hentKodeverkBetydning("Valutaer", true)
    }

    suspend fun hentStatsborgerskap(): Kodeverk {
        return hentKodeverkBetydning("StatsborgerskapFreg", true)
    }

    suspend fun hentDekningMedl(): Kodeverk {
        return hentKodeverkBetydning("DekningMedl", true)
    }

    suspend fun hentGrunnlagMedl(): Kodeverk {
        return hentKodeverkBetydning("GrunnlagMedl", true)
    }

    suspend fun hentSpraak(): Kodeverk {
        return hentKodeverkBetydning("Språk", true)
    }

    private suspend fun hentKodeverkBetydning(navn: String, eksluderUgyldige: Boolean): Kodeverk {
        return cache.get(navn) {
            runBlocking {
                kodeverkConsumer.fetchFromKodeverk(navn, eksluderUgyldige)
            }
        }
    }
}
