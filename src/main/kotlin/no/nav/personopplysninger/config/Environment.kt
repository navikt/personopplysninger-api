package no.nav.personopplysninger.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS"),
    val corsAllowedSchemes: String = System.getenv("CORS_ALLOWED_SCHEMES"),

    val kafkaBrokers: String = System.getenv("KAFKA_BROKERS"),
    val kafkaTruststorePath: String = System.getenv("KAFKA_TRUSTSTORE_PATH"),
    val kafkaKeystorePath: String = System.getenv("KAFKA_KEYSTORE_PATH"),
    val kafkaCredstorePassword: String = System.getenv("KAFKA_CREDSTORE_PASSWORD"),
    val varselHendelseTopic: String = System.getenv("VARSEL_HENDELSE_TOPIC"),

    val redirectUri: String = System.getenv("AUTH_REDIRECT_URI"),
    val frontendUri: String = System.getenv("AUTH_FRONTEND_URI"),
    val wellKnownUrl: String = System.getenv("IDPORTEN_WELL_KNOWN_URL"),
    val clientId: String = System.getenv("AUTH_CLIENT_ID"),
    val clientJwk: String = System.getenv("AUTH_CLIENT_JWK"),
    val encryptionKey: String = System.getenv("AUTH_ENCRYPTION_KEY"),

    val inst2Url: String = System.getenv("INST2_API_URL"),
    val kodeverkUrl: String = System.getenv("KODEVERK_REST_API_URL"),
    val norg2Url: String = System.getenv("NORG2_API_URL"),
    val digdirKrrProxyUrl: String = System.getenv("DIGDIR_KRR_PROXY_URL"),
    val pdlMottakUrl: String = System.getenv("PDL_MOTTAK_API_URL"),
    val pdlUrl: String = System.getenv("PDL_API_URL"),
    val medlUrl: String = System.getenv("MEDLEMSKAP_MEDL_API_URL"),
    val kontoregisterUrl: String = System.getenv("KONTOREGISTER_URL"),

    val inst2TargetApp: String = System.getenv("INST2_TARGET_APP"),
    val digdirKrrProxyTargetApp: String = System.getenv("DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP"),
    val medlTargetApp: String = System.getenv("MEDL_TARGET_APP"),
    val pdlTargetApp: String = System.getenv("PDL_CONSUMER_TARGET_APP"),
    val pdlMottakTargetApp: String = System.getenv("PDL_MOTTAK_TARGET_APP"),
    val kontoregisterTargetApp: String = System.getenv("KONTOREGISTER_TARGET_APP"),
    val kodeverkTargetApp: String = System.getenv("KODEVERK_TARGET_APP"),

    val subjectNameCacheThreshold: Long = (System.getenv("KODEVERK_CACHE_THRESHOLD") ?: 20L) as Long,
    val subjectNameCacheExpiryMinutes: Long = (System.getenv("KODEVERK_CACHE_EXPIRY_MINUTES") ?: 60L) as Long,
)