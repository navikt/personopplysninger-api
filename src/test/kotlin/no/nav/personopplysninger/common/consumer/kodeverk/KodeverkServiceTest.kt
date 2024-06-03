package no.nav.personopplysninger.consumer.kodeverk

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import no.nav.personopplysninger.consumer.kodeverk.dto.Kodeverk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class KodeverkServiceTest {

    private val cache: Cache<String, Kodeverk> = Caffeine.newBuilder().build()
    private val kodeverkConsumer: no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer = mockk()
    private val kodeverkService = KodeverkService(cache, kodeverkConsumer)

    @BeforeEach
    fun setup() {
        cache.invalidateAll()
    }

    @Test
    fun shouldReturnCachedValue() {
        coEvery {
            kodeverkConsumer.fetchFromKodeverk(any(), any())
        } returns kodeverkDummy("cached postnummer") andThen kodeverkDummy("new postnummer")

        val first = runBlocking { kodeverkService.hentPostnummer() }
        val second = runBlocking { kodeverkService.hentPostnummer() }

        assertEquals(first.navn, second.navn)
    }

    @Test
    fun shouldInvalidateCacheAndReturnNewValue() {
        coEvery {
            kodeverkConsumer.fetchFromKodeverk(any(), any())
        } returns kodeverkDummy("cached postnummer") andThen kodeverkDummy("new postnummer")

        val first = runBlocking { kodeverkService.hentPostnummer() }
        cache.invalidateAll()
        val second = runBlocking { kodeverkService.hentPostnummer() }

        assertNotEquals(first.navn, second.navn)
    }

    @Test
    fun shouldNotHitCacheWithDifferentKey() {
        coEvery {
            kodeverkConsumer.fetchFromKodeverk(any(), any())
        } returns kodeverkDummy("postnummer") andThen kodeverkDummy("kommune")

        val first = runBlocking { kodeverkService.hentPostnummer() }
        val second = runBlocking { kodeverkService.hentKommuner() }

        assertNotEquals(first.navn, second.navn)
    }

    private fun kodeverkDummy(navn: String): Kodeverk {
        return Kodeverk(navn = navn, emptyList())
    }

}