import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer

val cucumberVersion = "5.6.0"
val ktorVersion = "1.3.2"
val jacksonVersion = "2.10.4"
val konfigVersion = "1.6.10.0"
val kotlinLoggerVersion = "1.8.3"
val coroutinesVersion = "1.3.7"
val junitJupiterVersion = "5.6.2"
val logbackVersion = "1.2.3"
val logstashVersion = "6.4"

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

val mainClass = "no.nav.medlemskap.RunCucumberKt"

plugins {
    application
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.3.70"
}

group = "no.nav"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "no.nav.medlemskap.RunCucumberKt"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
    implementation("io.cucumber:cucumber-junit:${cucumberVersion}")
    implementation("io.cucumber:cucumber-java8:${cucumberVersion}")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggerVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.natpryce:konfig:$konfigVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    implementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/kotlin")
sourceSets["main"].resources.srcDirs("src/resources")

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "6.6"
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    manifest {
        attributes(
            mapOf(
                "Main-Class" to mainClass
            )
        )
    }
    transform(ServiceFileTransformer::class.java) {
        setPath("META-INF/cxf")
        include("bus-extensions.txt")
    }
}