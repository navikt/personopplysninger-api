package no.nav.personopplysninger.config

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter

class MetricsCollector(registry: CollectorRegistry) {
    val endreTelefonnummerCounter: Counter = Counter.build()
        .name("endring_telefonnummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av telefonnummer")
        .register(registry)

    val slettTelefonnummerCounter: Counter = Counter.build()
        .name("sletting_telefonnummer")
        .namespace(NAMESPACE)
        .help("Antall slettinger av telefonnummer")
        .register(registry)

    val endreNorskKontonummerCounter: Counter = Counter.build()
        .name("endring_norsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av norske kontonummer")
        .register(registry)

    val endreUtenlandskKontonummerCounter: Counter = Counter.build()
        .name("endring_utenlandsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av utenlandske kontonummer")
        .register(registry)

    val slettKontaktadresseCounter: Counter = Counter.build()
        .name("sletting_kontaktadresse")
        .namespace(NAMESPACE)
        .help("Antall slettinger av kontaktadresser")
        .register(registry)

    companion object {
        private const val NAMESPACE = "personbruker"
    }
}