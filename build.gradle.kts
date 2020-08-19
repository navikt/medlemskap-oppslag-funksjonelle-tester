val cucumberVersion = "5.6.0"
val ktorVersion = "1.3.2"
val jacksonVersion = "2.10.4"
val konfigVersion = "1.6.10.0"
val kotlinLoggerVersion = "1.8.3"

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
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
    implementation("io.cucumber:cucumber-junit:${cucumberVersion}")
    implementation("io.cucumber:cucumber-java8:${cucumberVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.natpryce:konfig:$konfigVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggerVersion")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/kotlin")
sourceSets["main"].resources.srcDirs("src/resources")