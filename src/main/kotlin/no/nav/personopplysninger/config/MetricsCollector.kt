package no.nav.personopplysninger.config

import io.prometheus.metrics.core.metrics.Counter
import io.prometheus.metrics.model.registry.PrometheusRegistry

class MetricsCollector(registry: PrometheusRegistry) {
    val endreTelefonnummerCounter: Counter = Counter.builder()
        .name("${NAMESPACE}_endring_telefonnummer")
        .help("Antall endringer av telefonnummer")
        .withoutExemplars()
        .register(registry)

    val slettTelefonnummerCounter: Counter = Counter.builder()
        .name("${NAMESPACE}_sletting_telefonnummer")
        .help("Antall slettinger av telefonnummer")
        .withoutExemplars()
        .register(registry)

    val endreNorskKontonummerCounter: Counter = Counter.builder()
        .name("${NAMESPACE}_endring_norsk_kontonummer")
        .help("Antall endringer av norske kontonummer")
        .withoutExemplars()
        .register(registry)

    val endreUtenlandskKontonummerCounter: Counter = Counter.builder()
        .name("${NAMESPACE}_endring_utenlandsk_kontonummer")
        .help("Antall endringer av utenlandske kontonummer")
        .withoutExemplars()
        .register(registry)

    val slettKontaktadresseCounter: Counter = Counter.builder()
        .name("${NAMESPACE}_sletting_kontaktadresse")
        .help("Antall slettinger av kontaktadresser")
        .withoutExemplars()
        .register(registry)

    companion object {
        private const val NAMESPACE = "personbruker"
    }
}