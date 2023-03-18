plugins {
    id("com.adarshr.test-logger")
    id("com.github.johnrengelman.shadow")
    id("io.gitlab.arturbosch.detekt")
    id("io.micronaut.application")
    id("io.micronaut.test-resources")
    id("org.jetbrains.dokka")
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
}

group = "org.powermilk"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19
java.targetCompatibility = JavaVersion.VERSION_19

val detektVersion: String by project
val jacksonVersion: String by project
val kotlinLoggingVersion: String by project
val log4jVersion: String by project
val log4jImplVersion: String by project

repositories {
    google()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    mavenLocal()
    mavenCentral()
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.data:micronaut-data-document-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation(kotlin("allopen"))
    implementation(kotlin("noarg"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.apache.logging.log4j:log4j-api:$log4jVersion")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    runtimeOnly("org.mongodb:mongodb-driver-reactivestreams")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
}

application {
    mainClass.set("org.powermilk.ApplicationKt")
}

kapt {
    useBuildCache = false
}

testlogger {
    showStackTraces = false
    showFullStackTraces = false
    showCauses = false
    @Suppress("MagicNumber")
    slowThreshold = 10000
    showSimpleNames = true
}

tasks {
    compileKotlin {
        kotlinOptions {
            allWarningsAsErrors = true
            verbose = true
            jvmTarget = java.targetCompatibility.toString()
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    compileTestKotlin {
        kotlinOptions {
            verbose = true
            jvmTarget = java.targetCompatibility.toString()
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    detekt.configure {
        reports {
            xml.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
            html.required.set(true)
            html.outputLocation.set(file("$buildDir/reports/detekt/detekt.html"))
        }
    }

    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka"))
        dokkaSourceSets {
            named("main") {
                jdkVersion.set(java.targetCompatibility.toString().toInt())
                skipDeprecated.set(false)
                includeNonPublic.set(true)
            }
        }
    }
}

graalvmNative.toolchainDetection.set(false)

micronaut {
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("org.powermilk.*")
    }
}



