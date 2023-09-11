import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    val versions = object {
        val kotlin = "1.9.0"
        val shadow = "8.1.1"
        val versions = "0.47.0"
    }
    
    kotlin("jvm") version(versions.kotlin)
    kotlin("plugin.allopen") version(versions.kotlin)
    kotlin("plugin.serialization") version(versions.kotlin)

    id("com.github.johnrengelman.shadow") version(versions.shadow)
    id("com.github.ben-manes.versions") version(versions.versions) // ./gradlew dependencyUpdates to check for new versions
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
        val caffeine = "3.1.7"
        val ktor = "2.3.2"
        val logback = "1.4.8"
        val logstash = "7.4"
        val micrometer = "1.11.2"
        val mockk = "1.13.5"
        val navSecurity = "3.1.5"
        val navCommon = "2.2023.01.10_13.49-81ddc732df3a"
        val unleash = "4.4.1"
        val kotlin = "1.9.0"
        val tmsKtorTokenSupport = "2.1.3"
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
    implementation("no.finn.unleash:unleash-client-java:${versions.unleash}")
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
