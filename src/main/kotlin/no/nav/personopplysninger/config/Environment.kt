package no.nav.personopplysninger.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS"),
    val corsAllowedSchemes: String = System.getenv("CORS_ALLOWED_SCHEMES"),

    val inst2Url: String = System.getenv("INST2_API_URL"),
    val kodeverkUrl: String = System.getenv("KODEVERK_REST_API_URL"),
    val norg2Url: String = System.getenv("NORG2_API_V1_URL"),
    val digdirKrrProxyUrl: String = System.getenv("DIGDIR_KRR_PROXY_URL"),
    val pdlMottakUrl: String = System.getenv("PDL_MOTTAK_API_URL"),
    val pdlUrl: String = System.getenv("PDL_API_URL"),
    val medlUrl: String = System.getenv("MEDLEMSKAP_MEDL_API_URL"),
    val kontoregisterUrl: String = System.getenv("KONTOREGISTER_URL"),

    val inst2TargetApp: String = System.getenv("PERSONOPPLYSNINGER_PROXY_TARGET_APP"), // todo: Endre navn på variabel
    val digdirKrrProxyTargetApp: String = System.getenv("DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP"),
    val medlTargetApp: String = System.getenv("MEDL_CONSUMER_TARGET_APP"),
    val personopplysningerProxyTargetApp: String = System.getenv("PERSONOPPLYSNINGER_PROXY_TARGET_APP"),
    val pdlTargetApp: String = System.getenv("PDL_CONSUMER_TARGET_APP"),
    val pdlMottakTargetApp: String = System.getenv("PDL_MOTTAK_TARGET_APP"),
    val kontoregisterTargetApp: String = System.getenv("KONTOREGISTER_TARGET_APP"),

    val subjectNameCacheThreshold: Long = (System.getenv("KODEVERK_CACHE_THRESHOLD") ?: 20L) as Long,
    val subjectNameCacheExpiryMinutes: Long = (System.getenv("KODEVERK_CACHE_EXPIRY_MINUTES") ?: 60L) as Long,
)