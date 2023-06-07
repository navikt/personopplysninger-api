import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm") version("1.8.21")
    kotlin("plugin.allopen") version("1.8.21")
    kotlin("plugin.serialization") version("1.8.21")

    id("com.github.johnrengelman.shadow") version ("8.1.1")
    id("com.github.ben-manes.versions") version ("0.46.0") // ./gradlew dependencyUpdates to check for new versions
    application
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    val versions = object {
        val auth0Jwt = "4.4.0"
        val caffeine = "3.1.6"
        val ktor = "2.3.1"
        val logback = "1.4.7"
        val logstash = "7.3"
        val micrometer = "1.11.0"
        val mockk = "1.13.5"
        val navSecurity = "3.1.0"
        val navCommon = "2.2023.01.10_13.49-81ddc732df3a"
        val finnUnleash = "4.4.1"
        val kotlin = "1.8.21"
        val shadow = "8.1.1"
        val tmsKtorTokenSupport = "2.0.1"
    }

    implementation("com.auth0:java-jwt:${versions.auth0Jwt}")
    implementation("com.github.ben-manes.caffeine:caffeine:${versions.caffeine}")
    implementation("io.ktor:ktor-serialization:${versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${versions.ktor}")
    implementation("io.ktor:ktor-server-call-logging:${versions.ktor}")
    implementation("io.ktor:ktor-server-core:${versions.ktor}")
    implementation("io.ktor:ktor-server-cors:${versions.ktor}")
    implementation("io.ktor:ktor-server-status-pages:${versions.ktor}")
    implementation("io.ktor:ktor-server-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-server-auth:${versions.ktor}")
    implementation("io.ktor:ktor-client-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-client-apache:${versions.ktor}")
    implementation("io.ktor:ktor-server-metrics-micrometer:${versions.ktor}")
    implementation("io.micrometer:micrometer-registry-prometheus:${versions.micrometer}")
    implementation("no.nav.security:token-validation-ktor-v2:${versions.navSecurity}")
    implementation("com.github.navikt.tms-ktor-token-support:token-support-tokendings-exchange:${versions.tmsKtorTokenSupport}")
    implementation("net.logstash.logback:logstash-logback-encoder:${versions.logstash}")
    implementation("ch.qos.logback:logback-classic:${versions.logback}")
    implementation("no.nav.common:feature-toggle:${versions.navCommon}")
    implementation("no.finn.unleash:unleash-client-java:${versions.finnUnleash}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${versions.kotlin}")
    testImplementation("io.ktor:ktor-server-test-host:${versions.ktor}")
    testImplementation("io.ktor:ktor-client-mock:${versions.ktor}")
    testImplementation("io.mockk:mockk:${versions.mockk}")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events("passed", "skipped", "failed")
        }
    }
}
