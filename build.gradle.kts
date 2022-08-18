import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm").version(Kotlin.version)
    kotlin("plugin.allopen").version(Kotlin.version)
    kotlin("plugin.serialization").version(Kotlin.version)

    id(Shadow.pluginId) version (Shadow.version)
    id(Versions.pluginId) version Versions.version // ./gradlew dependencyUpdates to check for new versions
    application
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(Auth0.jwt)
    implementation(Ktor.serverNetty)
    implementation(Ktor.serverCallLogging)
    implementation(Ktor.serverCore)
    implementation(Ktor.serialization)
    implementation(Ktor.serializationGson)
    implementation(Ktor.serverCors)
    implementation(Ktor.serverStatusPages)
    implementation(Ktor.serverContentNegotiation)
    implementation(Ktor.serverAuth)
    implementation(Ktor.clientContentNegotiation)
    implementation(Ktor.clientApache)
    implementation(DittNAV.Common.logging)
    implementation(NAV.Security.tokenValidationKtor)
    implementation(TmsKtorTokenSupport.tokendingsExchange)
    implementation(Kotlin.jdk8)
    implementation(Logstash.logbackEncoder)
    implementation(Logback.classic)
    implementation(Jackson.kotlinModule)
    implementation(Jackson.datatypeJsr310)
    implementation(NAV.Common.featureToggle)
    implementation(Finn.unleashClient)
    testImplementation(Kotlin.junit5)
    testImplementation(Ktor.serverTestHost)
    testImplementation(Ktor.clientMock)
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events("passed", "skipped", "failed")
        }
    }

    register("runServer", JavaExec::class) {

        environment("CORS_ALLOWED_ORIGINS", "localhost:9002")

        environment("NAIS_CLUSTER_NAME", "dev-gcp")
        environment("NAIS_NAMESPACE", "personbruker")

        main = application.mainClassName
        classpath = sourceSets["main"].runtimeClasspath
    }
}

apply(plugin = Shadow.pluginId)