package no.nav.personopplysninger.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS") ?: "",
    val corsAllowedSchemes: String = System.getenv("CORS_ALLOWED_SCHEMES") ?: "https",
    val inst2Url: String = System.getenv("INST2_API_URL") ?: "http://localhost:8090/inst2/",
    val kodeverkUrl: String = System.getenv("KODEVERK_REST_API_URL") ?: "http://localhost:8090/kodeverk/",
    val tpsProxyUrl: String = System.getenv("TPS_PROXY_API_V1_INNSYN_URL") ?: "http://localhost:8090/tps-proxy/",
    val norg2Url: String = System.getenv("NORG2_API_V1_URL") ?: "http://localhost:8090/norg2/",
    val digdirKrrProxyUrl: String = System.getenv("DIGDIR_KRR_PROXY_URL") ?: "http://localhost:8090/digdir-krr-proxy/",
    val pdlMottakUrl: String = System.getenv("PDL_MOTTAK_API_URL") ?: "http://localhost:8090/pdl-mottak/",
    val pdlUrl: String = System.getenv("PDL_API_URL") ?: "http://localhost:8090/pdl/",
    val medlUrl: String = System.getenv("MEDLEMSKAP_MEDL_API_URL") ?: "http://localhost:8090/medl/",
    val kontoregisterUrl: String = System.getenv("KONTOREGISTER_URL") ?: "http://localhost:8090/kontoregister/",
    val inst2TargetApp: String = System.getenv("PERSONOPPLYSNINGER_PROXY_TARGET_APP") ?: "", //TODO: Endre navn på variabel
    val digdirKrrProxyTargetApp: String = System.getenv("DIGDIR_KRR_PROXY_CONSUMER_TARGET_APP") ?: "",
    val medlTargetApp: String = System.getenv("MEDL_CONSUMER_TARGET_APP") ?: "",
    val personopplysningerProxyTargetApp: String = System.getenv("PERSONOPPLYSNINGER_PROXY_TARGET_APP") ?: "",
    val pdlTargetApp: String = System.getenv("PDL_CONSUMER_TARGET_APP") ?: "",
    val pdlMottakTargetApp: String = System.getenv("PDL_MOTTAK_TARGET_APP") ?: "",
    val kontoregisterTargetApp: String = System.getenv("KONTOREGISTER_TARGET_APP") ?: "",
    val securityJwksIssuer: String = "loginservice",
    val securityJwksUrl: String = System.getenv("LOGINSERVICE_IDPORTEN_DISCOVERY_URL") ?: "https://dummyUrl.com",
    val securityAudience: String = System.getenv("LOGINSERVICE_IDPORTEN_AUDIENCE") ?: "dummyAudience",
)